package com.example.data.repository

import com.example.data.local.database.FavoriteMoviesDatabase
import com.example.data.mapper.FavoriteMapper
import com.example.data.mapper.RemoteMapper
import com.example.data.remote.RemoteApi
import com.example.data.remote.services.MoviesApiService
import com.example.domain.model.MovieDto
import com.example.domain.repository.MoviesRepository
import io.reactivex.Completable

class MoviesDataRepository(
    private val remoteApi: RemoteApi,
    private val database: FavoriteMoviesDatabase,
    private val remoteMapper: RemoteMapper,
    private val favoriteMapper: FavoriteMapper
) : MoviesRepository {

    override fun fetchMovies(page: Int) = remoteApi.createService(MoviesApiService::class.java)
        .fetchMovies(page)
        .map { remoteMapper.map(it) }

    override fun getFavorites() = database.favoriteMoviesDao()
        .getFaviriteMovieEntities()
        .map { list -> list.map { favoriteMapper.mapToDto(it) } }

    override fun queryFavorites(query: String) = database.favoriteMoviesDao()
        .queryFavoriteMovieEntities(query)
        .map { list -> list.map { favoriteMapper.mapToDto(it) } }

    override fun insertFavorite(entity: MovieDto) = Completable.fromAction {
        database.favoriteMoviesDao().insertMovieEntity(favoriteMapper.mapToFavorite(entity))
    }

    override fun deleteFavorite(entity: MovieDto) = Completable.fromAction {
        database.favoriteMoviesDao().deleteMovieEntity(favoriteMapper.mapToFavorite(entity))
    }
}