package com.example.data.mapper

import com.example.data.local.entity.FavoriteMovieDataEntity
import com.example.domain.model.MovieDto

class FavoriteMapper {

    fun mapToDto(favorite: FavoriteMovieDataEntity) = MovieDto(
        favorite.id,
        favorite.title,
        favorite.poster
    )

    fun mapToFavorite(movieDto: MovieDto) = FavoriteMovieDataEntity(
        movieDto.id,
        movieDto.title,
        movieDto.poster
    )
}