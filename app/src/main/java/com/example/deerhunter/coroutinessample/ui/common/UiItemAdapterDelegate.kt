package com.example.deerhunter.coroutinessample.ui.common

import android.support.v7.widget.RecyclerView
import com.example.deerhunter.coroutinessample.ui.movie.view.items.UiItem
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate

abstract class UiItemAdapterDelegate<I : UiItem, VH : RecyclerView.ViewHolder>
    : AbsListItemAdapterDelegate<I, UiItem, VH>()