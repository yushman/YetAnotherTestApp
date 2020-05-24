package com.example.yetanothertestapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yetanothertestapp.R
import com.example.yetanothertestapp.databinding.ItemFooterBinding
import com.example.yetanothertestapp.databinding.ItemFooterErrorBinding
import com.example.yetanothertestapp.databinding.ItemRvBinding
import com.example.yetanothertestapp.extensions.gone
import com.example.yetanothertestapp.extensions.visible
import com.example.yetanothertestapp.model.MovieViewItem

class MoviesAdapter(
    private val listener: (item: MovieViewItem, isLongTap: Boolean) -> Unit
    ) : RecyclerView.Adapter<MoviesAdapter.AMoviesViewHolder>() {

    private var moviesList = listOf<MovieViewItem>()

    override fun getItemViewType(position: Int): Int {
        return when (moviesList[position]) {
            is MovieViewItem.FooterLoading -> ITEM_FOOTER
            is MovieViewItem.MovieItem -> ITEM_MOVIE
            is MovieViewItem.FooterLoadingError -> ITEM_ERROR_FOOTER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AMoviesViewHolder {
        val li = LayoutInflater.from(parent.context)
        return when (viewType){
            ITEM_FOOTER -> FooterViewHolder(ItemFooterBinding.inflate(li, parent, false))
            ITEM_MOVIE -> MoviesViewHolder(ItemRvBinding.inflate(li, parent, false))
            else -> FooterErrorViewHolder(ItemFooterErrorBinding.inflate(li, parent, false))
        }
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: AMoviesViewHolder, position: Int) {
        when (holder) {
            is MoviesViewHolder -> holder.bind(
                moviesList[position] as MovieViewItem.MovieItem,
                listener
            )
            is FooterErrorViewHolder -> holder.bind(listener)
        }
    }

    fun update(newList: List<MovieViewItem>){
        val diffCallback = object : DiffUtil.Callback(){
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return moviesList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize(): Int {
                return moviesList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return moviesList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) = Any()

        }
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
        moviesList = newList
    }

    companion object {
        const val ITEM_FOOTER = 0
        const val ITEM_ERROR_FOOTER = 1
        const val ITEM_MOVIE = 2
    }

    abstract class AMoviesViewHolder(v: View) : RecyclerView.ViewHolder(v)

    class MoviesViewHolder(val binding: ItemRvBinding): AMoviesViewHolder(binding.root){

        fun bind(
            item: MovieViewItem.MovieItem,
            listener: (item: MovieViewItem, isLongTap: Boolean) -> Unit
        ) {
            binding.tvItemTitle.text = item.title

            if (item.isFavorite) binding.ivItemFavorite.visible() else binding.ivItemFavorite.gone()

            Glide.with(binding.ivItemPoster).load(item.poster)
                .centerInside()
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_image_black_24dp)
                .into(binding.ivItemPoster)

            binding.root.setOnClickListener { listener.invoke(item, false) }
            binding.root.setOnLongClickListener {
                listener.invoke(item, true)
                true
            }
        }
    }

    class FooterViewHolder(binding: ItemFooterBinding): AMoviesViewHolder(binding.root)

    class FooterErrorViewHolder(val binding: ItemFooterErrorBinding) :
        AMoviesViewHolder(binding.root) {
        fun bind(listener: (item: MovieViewItem, isLongTap: Boolean) -> Unit) {
            binding.btnItem.setOnClickListener {
                listener.invoke(
                    MovieViewItem.FooterLoadingError,
                    false
                )
            }
        }
    }

}