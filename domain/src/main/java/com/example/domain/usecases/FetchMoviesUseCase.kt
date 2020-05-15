package com.example.domain.usecases

import com.example.domain.repository.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(val moviesRepository: MoviesRepository) : UseCase {

    fun fetchMovies(page: Int) = moviesRepository.fetchMovies(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}