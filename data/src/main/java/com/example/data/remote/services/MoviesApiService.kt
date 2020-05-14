package com.example.data.remote.services

import com.example.data.remote.model.MoviesDataResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("public-api/v1.4/movies/")
    fun fetchMovies(@Query("page") page: Int): Single<MoviesDataResponse>
}