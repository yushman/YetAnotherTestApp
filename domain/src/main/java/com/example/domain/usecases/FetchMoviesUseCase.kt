package com.example.domain.usecases

import com.example.domain.model.MoviesResponse
import com.example.domain.repository.MoviesRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FetchMoviesUseCase(val moviesRepository: MoviesRepository) : UseCase {
    private var currentPage: Int = 1

    fun fetchMovies(page: Int = currentPage) = moviesRepository.fetchMovies(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun fetchNextPage() = fetchMovies(++currentPage)

    fun fetchUpdate(): Single<MoviesResponse> {
        currentPage = 1
        return fetchMovies()
    }
}