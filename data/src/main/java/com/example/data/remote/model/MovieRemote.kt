package com.example.data.remote.model

import android.icu.text.CaseMap

data class MovieRemote(
    val id: Int,
    val title: String,
    val poster: Poster
){
    data class Poster(val image: String)
}

