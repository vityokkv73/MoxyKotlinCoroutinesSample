package com.example.deerhunter.coroutinessample.ui.common

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.example.deerhunter.coroutinessample.R
import com.example.deerhunter.coroutinessample.ui.movie.view.items.LoadMoreProgressItem
import com.example.deerhunter.coroutinessample.ui.movie.view.items.UiItem
import com.example.deerhunter.coroutinessample.utilities.inflate
import com.example.deerhunter.coroutinessample.utilities.setHeight

class LoadMoreProgressAdapterDelegate
    : UiItemAdapterDelegate<LoadMoreProgressItem, DumbViewHolder>() {

    override fun isForViewType(item: UiItem, items: MutableList<UiItem>, position: Int): Boolean =
            item is LoadMoreProgressItem

    override fun onBindViewHolder(item: LoadMoreProgressItem, viewHolder: DumbViewHolder, payloads: MutableList<Any>) {
        viewHolder.itemView.setHeight(if (item.position == 0) MATCH_PARENT else WRAP_CONTENT)
    }

    override fun onCreateViewHolder(parent: ViewGroup) = DumbViewHolder(parent.inflate(R.layout.progress_item))
}