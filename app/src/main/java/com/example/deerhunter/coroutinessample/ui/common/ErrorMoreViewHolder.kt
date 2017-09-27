package com.example.deerhunter.coroutinessample.ui.common

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.deerhunter.coroutinessample.R
import com.example.deerhunter.coroutinessample.ui.movie.view.items.LoadMoreErrorItem
import com.example.deerhunter.coroutinessample.utilities.inflate
import com.example.deerhunter.coroutinessample.utilities.makeGone
import com.example.deerhunter.coroutinessample.utilities.makeVisible
import kotlinx.android.synthetic.main.error_view.view.*

class LoadMoreErrorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(clickListener: (Int, LoadMoreErrorItem) -> Unit, item: LoadMoreErrorItem): LoadMoreErrorViewHolder {
        with(itemView) {
            if (item.position == 0) {
                errorLogo.makeVisible()
            } else {
                errorLogo.makeGone()
            }
            item.errorMainMessage?.let { errorMainMessage.text = it }
            item.errorAdditionalMessage?.let { errorAdditionalMessage.text = it }
            errorRetryButton.setOnClickListener { clickListener(R.id.errorRetryButton, item) }
        }
        return this
    }

    companion object {
        fun createViewHolder(parent: ViewGroup) = LoadMoreErrorViewHolder(parent.inflate(R.layout.error_view))
    }
}