package com.example.yetanothertestapp.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.MovieDto
import com.example.domain.model.MoviesResponse
import com.example.domain.usecases.FetchMoviesUseCase
import com.example.domain.usecases.GetFavoritesUseCase
import io.reactivex.disposables.Disposables

class MoviesFragmentViewModel
constructor(
    val fetchMoviesUseCase: FetchMoviesUseCase,
    val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    var isSearchViewActive: Boolean = false
    var isEmptyQuery: Boolean = false
    var isInLoading: Boolean = false
    val state: MutableLiveData<State> = MutableLiveData()
    var sub = Disposables.empty()

    override fun onCleared() {
        if (!sub.isDisposed) sub.dispose()
        super.onCleared()
    }

    fun proceedQuery(text: String) {
        state.value = State.LoadingState()
        sub = getFavoritesUseCase.queryFavirites(text).subscribe { r, e -> handleQueryResult(r, e) }
    }

    private fun handleQueryResult(result: List<MovieDto>?, e: Throwable?) {
        if (e == null && result != null)
            state.value = State.LoadedState(result)
        else state.value = State.ErrorState(e!!.message!!)
    }

    fun loadMovies() {
        state.value = State.LoadingState()
        sub = fetchMoviesUseCase.fetchUpdate().subscribe { r, e -> handleLoadResult(r, e) }
    }

    private fun handleLoadResult(r: MoviesResponse?, e: Throwable?) {

    }

    fun updateList() {

    }

    sealed class State {
        class ErrorState (val message: String) : State()
        class NoItemState : State()
        class LoadingState : State()
        class LoadedState <T> (val data: List<T>) : State()
    }

}
