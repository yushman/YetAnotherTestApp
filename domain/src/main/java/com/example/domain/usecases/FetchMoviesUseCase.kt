package com.example.domain.usecases

import com.example.domain.repository.MoviesRepository

class FetchMoviesUseCase(val moviesRepository: MoviesRepository) : UseCase {
    fun fetchMovies(){
        moviesRepository
    }
}