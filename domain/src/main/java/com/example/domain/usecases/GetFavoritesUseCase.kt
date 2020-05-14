package com.example.domain.usecases

import com.example.domain.model.MovieDto
import com.example.domain.repository.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetFavoritesUseCase(val moviesRepository: MoviesRepository) : UseCase {

    fun getFavorites() = moviesRepository.getFavorites()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun queryFavirites(query: String) = moviesRepository.queryFavorites(query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun insertFavorite(entity: MovieDto) = moviesRepository.insertFavorite(entity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun removeFavorite(entity: MovieDto) = moviesRepository.deleteFavorite(entity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}