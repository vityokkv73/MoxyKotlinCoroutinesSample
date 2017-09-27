package com.example.deerhunter.coroutinessample.ui.common

import android.view.ViewGroup
import com.example.deerhunter.coroutinessample.ui.movie.view.items.LoadMoreErrorItem
import com.example.deerhunter.coroutinessample.ui.movie.view.items.UiItem

class LoadMoreErrorViewAdapterDelegate(private val clickListener: (Int, LoadMoreErrorItem)-> Unit)
    : UiItemAdapterDelegate<LoadMoreErrorItem, LoadMoreErrorViewHolder>() {

    override fun isForViewType(item: UiItem, items: MutableList<UiItem>, position: Int): Boolean =
            item is LoadMoreErrorItem

    override fun onBindViewHolder(item: LoadMoreErrorItem, viewHolder: LoadMoreErrorViewHolder, payloads: MutableList<Any>) {
        viewHolder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup) = LoadMoreErrorViewHolder.createViewHolder(parent)

}