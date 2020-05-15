package com.example.yetanothertestapp.ui.custom

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yetanothertestapp.ui.adapter.MoviesAdapter

class FooterGridLayoutManager(context: Context, spanCount: Int, val adapter: MoviesAdapter) :
    GridLayoutManager(context, spanCount) {
    init {
        val spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int) =
                if (adapter.getItemViewType(position) != MoviesAdapter.ITEM_MOVIE) spanCount
                else 1
        }
        spanSizeLookup.isSpanIndexCacheEnabled = true
        setSpanSizeLookup(spanSizeLookup)
    }
}