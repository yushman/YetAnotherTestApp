package com.example.data.remote.model

data class MoviesResponse(
    val count: Int,
    val next: String,
    val previouse: String,
    val results: List<MovieRemote>
)