package com.example.data.repository

import com.example.data.local.dao.FavoriteMoviesDao
import com.example.data.local.database.FavoriteMoviesDatabase
import com.example.data.local.entity.FavoriteMovieDataEntity
import com.example.data.remote.RemoteApi
import com.example.data.remote.services.MoviesApiService
import com.example.domain.repository.MoviesRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesDataRepository(
    val remoteApi: RemoteApi,
    val database: FavoriteMoviesDatabase): MoviesRepository {

    override fun fetchMovies(page: Int) = remoteApi.createService(MoviesApiService::class.java)
        .fetchMovies(page)

    override fun getFavorites() = database.favoriteMoviesDao()
        .getFaviriteMovieEntities()

    override fun insertFavorite(entity: FavoriteMovieDataEntity) = Completable.fromAction {
        database.favoriteMoviesDao().insertMovieEntity(entity)
    }
}