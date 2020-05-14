package com.example.data.remote.model

data class MoviesDataResponse(
    val count: Int,
    val next: String,
    val previouse: String,
    val results: List<MovieDataRemote>
)