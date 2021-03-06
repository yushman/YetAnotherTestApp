package com.example.yetanothertestapp.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.MovieDto
import com.example.domain.model.MoviesResponse
import com.example.domain.usecases.FetchMoviesUseCase
import com.example.domain.usecases.GetFavoritesUseCase
import com.example.yetanothertestapp.mapper.ViewItemMapper
import com.example.yetanothertestapp.model.MovieViewItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import timber.log.Timber

class MoviesFragmentViewModel(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val mapper = ViewItemMapper()
    val state: MutableLiveData<State> = MutableLiveData()

    private var favoritesList = listOf<MovieDto>()
    private var remoteList = mutableListOf<MovieDto>()
    private var sub = Disposables.empty()
    private var currentPage = 1

    override fun onCleared() {
        if (!sub.isDisposed) sub.dispose()
        super.onCleared()
    }

    fun addFavorite(item: MovieViewItem.MovieItem) {
        sub = getFavoritesUseCase.insertFavorite(mapper.mapFromViewItem(item))
            .doOnError { handleError(it) }
            .doOnComplete { getFavorites() }
            .subscribe()
    }

    fun removeFavorite(item: MovieViewItem.MovieItem) {
        sub = getFavoritesUseCase.removeFavorite(mapper.mapFromViewItem(item))
            .doOnError { handleError(it) }
            .doOnComplete { getFavorites() }
            .subscribe()
    }

    fun proceedQuery(text: String) {
        Timber.i("Quering from local")
        state.value = State.LoadingState
        sub = getFavoritesUseCase.queryInFavorites("%$text%")
            .doOnError { handleError(it) }
            .map { mapper.mapToViewItem(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { r -> handleQueryResult(r) }
    }

    fun updateList() {
        Timber.i("Updating from remote")
        currentPage = 1
        loadMovies(isReloading = true)
    }

    fun loadNext() {
        Timber.i("Loading next from remote")
        if (state.value is State.LoadingState) return
        if (state.value is State.ErrorState) return
        currentPage++
        loadMovies()
    }

    fun reloadPage() {
        Timber.i("Reloading from remote")
        loadMovies()
    }

    fun getFavorites() {
        sub = getFavoritesUseCase.getFavorites().subscribe { r, e -> handleGetFavoritesList(r, e) }
    }

    fun loadMovies(isReloading: Boolean = false) {
        if (state.value is State.LoadingState) return
        state.value = State.LoadingState
        sub = fetchMoviesUseCase.fetchMovies(currentPage)
            .doOnSuccess {
                if (isReloading) remoteList = it.results.toMutableList()
                else remoteList.addAll(it.results)
                state.postValue(
                    State.LoadedState(
                        mapper.mapToViewItem(
                            it,
                            remoteList,
                            favoritesList
                        )
                    )
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { r, e -> handleLoadResult(r, e) }
    }

    private fun handleGetFavoritesList(result: List<MovieDto>?, e: Throwable?) {
        if (e == null && result != null) favoritesList = result
        if (e != null) handleError(e)
    }

    private fun handleQueryResult(result: List<MovieViewItem>) {
        state.value = if (result.isNullOrEmpty()) State.NoItemState
        else State.LoadedState(result)
    }

    private fun handleError(e: Throwable) {
        state.value = State.ErrorState(e.message!!)
    }

    private fun handleLoadResult(result: MoviesResponse?, e: Throwable?) {
        if (e != null) {
            val list = mapper.mapToViewItem(result, remoteList, favoritesList)
            list.add(MovieViewItem.FooterLoadingError)
            state.value = State.LoadedState(list)
            state.value = State.ErrorState(e.message!!)
        }
    }

    private fun clearSub() {
        if (!sub.isDisposed) sub.dispose()
    }

    sealed class State {
        class ErrorState (val message: String) : State()
        object NoItemState : State()
        object LoadingState : State()
        class LoadedState(val data: List<MovieViewItem>) : State()
    }

}
