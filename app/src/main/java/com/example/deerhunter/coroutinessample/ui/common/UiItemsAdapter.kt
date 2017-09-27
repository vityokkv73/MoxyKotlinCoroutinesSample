package com.example.deerhunter.coroutinessample.ui.common

import com.example.deerhunter.coroutinessample.ui.movie.view.items.LoadMoreErrorItem
import com.example.deerhunter.coroutinessample.ui.movie.view.items.LoadMoreProgressItem
import com.example.deerhunter.coroutinessample.ui.movie.view.items.UiItem
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

abstract class UiItemsAdapter : ListDelegationAdapter<MutableList<UiItem>> {

    constructor() : super()
    constructor(delegatesManager: AdapterDelegatesManager<MutableList<UiItem>>) : super(delegatesManager)

    override fun setItems(items: MutableList<UiItem>) {
        super.setItems(items)
        notifyDataSetChanged()
    }

    fun add(list: List<UiItem>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun progress() {
        removeErrorAndProgress()
        items.add(LoadMoreProgressItem(itemCount))
        notifyDataSetChanged()
    }

    fun error(errorMainMessage: CharSequence? = null, errorAdditionalMessage: CharSequence? = null) {
        removeErrorAndProgress()
        items.add(LoadMoreErrorItem(itemCount, errorMainMessage, errorAdditionalMessage))
        notifyDataSetChanged()
    }

    fun removeErrorAndProgress() {
        items.removeAll { it is LoadMoreErrorItem || it is LoadMoreProgressItem }
    }
}