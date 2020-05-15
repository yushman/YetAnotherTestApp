package com.example.yetanothertestapp.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.MovieDto
import com.example.domain.usecases.FetchMoviesUseCase
import com.example.domain.usecases.GetFavoritesUseCase
import com.example.yetanothertestapp.mapper.ViewItemMapper
import com.example.yetanothertestapp.model.MovieViewItem
import io.reactivex.disposables.Disposables
import javax.inject.Inject

class MoviesFragmentViewModel
@Inject
constructor(
    val fetchMoviesUseCase: FetchMoviesUseCase,
    val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    val mapper = ViewItemMapper()
    val state: MutableLiveData<State> = MutableLiveData()

    var favoritesList = listOf<MovieDto>()
    var remoteList = mutableListOf<MovieDto>()
    var sub = Disposables.empty()
    var currentPage = 1

    init {
        getFavorites()
        loadMovies()
    }

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
        state.value = State.LoadingState()
        sub = getFavoritesUseCase.queryFavirites(text)
            .doOnError { handleError(it) }
            .map { mapper.mapToViewItem(it) }
            .subscribe { r -> handleQueryResult(r) }
    }

    fun loadMovies() {
        state.value = State.LoadingState()
        sub = fetchMoviesUseCase.fetchMovies(currentPage)
            .doOnError { handleError(it) }
            .doOnSuccess { remoteList.addAll(it.results) }
            .map { mapper.mapToViewItem(it, remoteList, favoritesList) }
            .subscribe { r, e -> handleLoadResult(r, e) }
    }

    fun updateList() {
        currentPage = 1
        loadMovies()
    }

    fun loadNext() {
        if (state.value is State.LoadingState) return
        currentPage++
        loadMovies()
    }

    fun reloadPage() {
        if (state.value is State.ErrorState)
            loadMovies()
    }

    private fun getFavorites() {
        sub = getFavoritesUseCase.getFavorites().subscribe { r, e -> handleGetFavoritesList(r, e) }
    }

    private fun handleGetFavoritesList(result: List<MovieDto>?, e: Throwable?) {
        if (e == null && result != null) favoritesList = result
    }

    private fun handleQueryResult(result: List<MovieViewItem>) {
        state.value = if (result.isNullOrEmpty()) State.NoItemState()
        else State.LoadedState(result)
    }

    private fun handleError(e: Throwable) {
        state.value = State.ErrorState(e.message!!)
    }

    private fun handleLoadResult(result: MutableList<MovieViewItem>, e: Throwable?) {
        if (e != null) result.add(MovieViewItem.FooterLoadingError)
        state.value = State.LoadedState(result)
    }

    sealed class State {
        class ErrorState (val message: String) : State()
        class NoItemState : State()
        class LoadingState : State()
        class LoadedState(val data: List<MovieViewItem>) : State()
    }

}
