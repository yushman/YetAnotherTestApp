package com.example.domain.usecases

import com.example.domain.repository.MoviesRepository
import io.reactivex.schedulers.Schedulers

class FetchMoviesUseCase(private val moviesRepository: MoviesRepository) : UseCase {

    fun fetchMovies(page: Int) = moviesRepository.fetchMovies(page)
        .subscribeOn(Schedulers.io())

}