package com.example.yetanothertestapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.yetanothertestapp.R
import com.example.yetanothertestapp.databinding.ItemFooterBinding
import com.example.yetanothertestapp.databinding.ItemRvBinding
import com.example.yetanothertestapp.extensions.gone
import com.example.yetanothertestapp.extensions.visible
import com.example.yetanothertestapp.model.MovieViewItem
import com.squareup.picasso.Picasso

class MoviesAdapter(
    private val listener: (item: MovieViewItem.MovieItem, isLongTap: Boolean) -> Unit
    ) : RecyclerView.Adapter<MoviesAdapter.AMoviesViewHolder>() {

    private val ITEM_FOOTER = 0
    private val ITEM_MOVIE = 1

    private var moviesList = listOf<MovieViewItem>()

    override fun getItemViewType(position: Int): Int {
        return when (moviesList[position]) {
            is MovieViewItem.Footer -> ITEM_FOOTER
            is MovieViewItem.MovieItem -> ITEM_MOVIE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AMoviesViewHolder {
        val li = LayoutInflater.from(parent.context)
        return when (viewType){
            ITEM_FOOTER -> FooterViewHolder(ItemFooterBinding.inflate(li, parent, false))
            ITEM_MOVIE -> MoviesViewHolder(ItemRvBinding.inflate(li, parent, false))
            else -> FooterViewHolder(ItemFooterBinding.inflate(li, parent, false))
        }
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: AMoviesViewHolder, position: Int) {
        if (holder is MoviesViewHolder)
            holder.bind(moviesList[position] as MovieViewItem.MovieItem, listener)
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

    abstract class AMoviesViewHolder(v: View) : RecyclerView.ViewHolder(v)

    class MoviesViewHolder(val binding: ItemRvBinding): AMoviesViewHolder(binding.root){

        fun bind(item: MovieViewItem.MovieItem, listener: (item: MovieViewItem.MovieItem, isLongTap: Boolean) -> Unit){
            binding.tvItemTitle.text = item.title

            if (item.isFavorite) binding.ivItemFavorite.visible() else binding.ivItemFavorite.gone()

            Picasso.get().load(item.poster)
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
}