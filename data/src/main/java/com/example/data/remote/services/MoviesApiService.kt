package com.example.data.remote.services

import com.example.data.remote.model.MoviesResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("public-api/v1.4/movies/")
    fun fetchMovies(@Query("page") page: Int): Single<MoviesResponse>
}