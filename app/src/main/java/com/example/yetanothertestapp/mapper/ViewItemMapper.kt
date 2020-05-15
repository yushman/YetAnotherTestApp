package com.example.yetanothertestapp.mapper

import com.example.domain.model.MovieDto
import com.example.domain.model.MoviesResponse
import com.example.yetanothertestapp.model.MovieViewItem

class ViewItemMapper {

    fun mapToViewItem(list: List<MovieDto>) =
        list.map {
            MovieViewItem.MovieItem(
                it.id,
                it.title,
                it.poster,
                isFavorite = true
            )
        }

    fun mapToViewItem(
        moviesResponse: MoviesResponse,
        oldList: List<MovieDto>,
        favorites: List<MovieDto>
    ): MutableList<MovieViewItem> {
        val result = mutableListOf<MovieViewItem>()
        oldList.forEach {
            result.add(
                MovieViewItem.MovieItem(
                    it.id,
                    it.title,
                    it.poster,
                    isFavorite = favorites.contains(it)
                )
            )
        }
//        moviesResponse.results.forEach {
//            result.add(
//                MovieViewItem.MovieItem(
//                    it.id,
//                    it.title,
//                    it.poster,
//                    isFavorite = favorites.contains(it)
//            ))
//        }
        if (moviesResponse.next != null)
            result.add(MovieViewItem.FooterLoading)
        return result
    }

    fun mapFromViewItem(item: MovieViewItem.MovieItem) =
        MovieDto(
            item.id,
            item.title,
            item.poster
        )

}