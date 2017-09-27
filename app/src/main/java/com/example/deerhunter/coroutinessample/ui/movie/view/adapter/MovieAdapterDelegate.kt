package com.example.deerhunter.coroutinessample.ui.movie.view.adapter

import android.view.ViewGroup
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.ui.common.UiItemAdapterDelegate
import com.example.deerhunter.coroutinessample.ui.movie.view.items.MovieItem
import com.example.deerhunter.coroutinessample.ui.movie.view.items.UiItem
import com.example.deerhunter.coroutinessample.ui.utilities.UiCalculator

class MovieAdapterDelegate(private val rowLayoutData: UiCalculator.RowLayoutData, private val clickListener: (Movie) -> Unit)
    : UiItemAdapterDelegate<MovieItem, MovieViewHolder>() {

    override fun isForViewType(item: UiItem, items: MutableList<UiItem>, position: Int) = item is MovieItem

    override fun onCreateViewHolder(parent: ViewGroup): MovieViewHolder {
        return MovieViewHolder.createViewHolder(parent, rowLayoutData)
    }

    override fun onBindViewHolder(item: MovieItem, viewHolder: MovieViewHolder, payloads: MutableList<Any>) {
        viewHolder.bind(item.movie, clickListener)
    }
}