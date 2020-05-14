package com.example.yetanothertestapp.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yetanothertestapp.model.MovieViewItem

class MoviesFragmentViewModel() : ViewModel(){
    var isSearchViewActive: Boolean = false
    var isEmptyQuery: Boolean = false
    var isInLoading: Boolean = false
    val state: MutableLiveData<State> = MutableLiveData()

    fun proceedQuery(text: String) {

    }

    fun loadMovies() {

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
