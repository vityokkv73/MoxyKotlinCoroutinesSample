package com.example.deerhunter.coroutinessample.ui.utilities

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import timber.log.Timber

class OnPageScrollListener(private val requestNext: () -> Unit,
                           private val preloadRange: Int = DEFAULT_PRELOAD_RANGE) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager
        val lastVisibleItemPosition = when (layoutManager) {
            is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
            is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
            else -> {
                Timber.e("Ошибка layoutManager")
                -1
            }
        }
        val adapter = recyclerView.adapter

        if (adapter.itemCount - lastVisibleItemPosition < preloadRange) {
            requestNext()
        }
    }

    companion object {
        const val DEFAULT_PRELOAD_RANGE = 5
    }
}