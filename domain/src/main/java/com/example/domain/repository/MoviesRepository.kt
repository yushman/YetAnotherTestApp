package com.example.domain.repository

import com.example.data.local.entity.FavoriteMovieDataEntity
import com.example.data.remote.model.MovieRemote
import com.example.data.remote.model.MoviesResponse
import io.reactivex.Completable
import io.reactivex.Single

interface MoviesRepository {

    fun fetchMovies(page: Int): Single<MoviesResponse>

    fun getFavorites(): Single<List<FavoriteMovieDataEntity>>

    fun insertFavorite(entity: FavoriteMovieDataEntity): Completable
}