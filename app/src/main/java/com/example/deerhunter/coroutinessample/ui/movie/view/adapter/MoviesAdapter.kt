package com.example.deerhunter.coroutinessample.ui.movie.view.adapter

import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.ui.common.LoadMoreErrorViewAdapterDelegate
import com.example.deerhunter.coroutinessample.ui.common.LoadMoreProgressAdapterDelegate
import com.example.deerhunter.coroutinessample.ui.common.UiItemsAdapter
import com.example.deerhunter.coroutinessample.ui.movie.view.items.LoadMoreErrorItem
import com.example.deerhunter.coroutinessample.ui.utilities.UiCalculator
import java.util.*

class MoviesAdapter(rowLayoutData: UiCalculator.RowLayoutData, listener: (Movie) -> Unit, loadMoreClickListener: (Int, LoadMoreErrorItem) -> Unit) : UiItemsAdapter() {
    init {
        items = ArrayList()
        delegatesManager.addDelegate(MovieAdapterDelegate(rowLayoutData, listener))
        delegatesManager.addDelegate(LoadMoreProgressAdapterDelegate())
        delegatesManager.addDelegate(LoadMoreErrorViewAdapterDelegate(loadMoreClickListener))
    }
}
