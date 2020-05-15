package com.example.yetanothertestapp.model

sealed class MovieViewItem{

    object FooterLoading : MovieViewItem()
    object FooterLoadingError : MovieViewItem()
    data class MovieItem(
        val id: Int,
        val title: String,
        val poster: String,
        val isFavorite: Boolean = false
    ): MovieViewItem()
}
