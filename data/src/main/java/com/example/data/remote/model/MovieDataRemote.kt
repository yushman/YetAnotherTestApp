package com.example.data.remote.model

data class MovieDataRemote(
    val id: Int,
    val title: String,
    val poster: Poster
){
    data class Poster(val image: String)
}

