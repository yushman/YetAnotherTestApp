package com.example.yetanothertestapp.model

sealed class MovieViewItem{

    object Footer : MovieViewItem()
    data class MovieItem(
        val id: Int,
        val title: String,
        val poster: String,
        val isFavorite: Boolean = false
    ): MovieViewItem()
}
