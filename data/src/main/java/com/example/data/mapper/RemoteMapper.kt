package com.example.data.mapper

import com.example.data.remote.model.MoviesDataResponse
import com.example.domain.model.MovieDto
import com.example.domain.model.MoviesResponse

class RemoteMapper {
    fun map(movieDataResponse: MoviesDataResponse): MoviesResponse {
        val movies = movieDataResponse.results.map { MovieDto(it.id, it.title, it.poster.image) }
        return MoviesResponse(
            movieDataResponse.count,
            movieDataResponse.next,
            movieDataResponse.previouse,
            movies
        )
    }
}