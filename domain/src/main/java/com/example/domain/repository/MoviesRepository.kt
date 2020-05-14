package com.example.domain.repository

import com.example.domain.model.MovieDto
import com.example.domain.model.MoviesResponse
import io.reactivex.Completable
import io.reactivex.Single

interface MoviesRepository {

    fun fetchMovies(page: Int): Single<MoviesResponse>

    fun getFavorites(): Single<List<MovieDto>>

    fun queryFavorites(query: String): Single<List<MovieDto>>

    fun insertFavorite(entity: MovieDto): Completable

    fun deleteFavorite(entity: MovieDto): Completable
}