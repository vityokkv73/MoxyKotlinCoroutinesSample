package com.example.deerhunter.coroutinessample.ui.movie.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.deerhunter.coroutinessample.R
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.ui.utilities.UiCalculator
import com.example.deerhunter.coroutinessample.utilities.inflate
import com.example.deerhunter.coroutinessample.utilities.loadImage
import com.example.deerhunter.coroutinessample.utilities.setSize
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.movie_card.*

class MovieViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(movie: Movie, clickListener: (Movie) -> Unit): MovieViewHolder {
        title.text = movie.originalTitle
        rating.text = movie.voteAverage.toString()
        logo.loadImage(movie.posterPath)
        itemView.setOnClickListener { clickListener(movie) }
        return this
    }

    companion object {
        fun createViewHolder(parent: ViewGroup, rowData: UiCalculator.RowLayoutData): MovieViewHolder {
            return MovieViewHolder(parent.inflate(R.layout.movie_card).apply { setSize(rowData.movieCardSize) })
        }
    }
}