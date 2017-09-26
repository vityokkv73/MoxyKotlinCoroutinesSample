package com.example.deerhunter.coroutinessample.ui.movie.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.deerhunter.coroutinessample.R
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.utilities.loadImage
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.movie_card.view.*

class MovieAdapterDelegate(private val context: Context, var clickListener: (Movie) -> Unit) : AdapterDelegate<List<Movie>>() {

    override fun isForViewType(items: List<Movie>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_card, parent, false))
    }

    override fun onBindViewHolder(items: List<Movie>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val movieViewHolder = holder as MovieViewHolder
        val movie = items[position]

        movieViewHolder.itemView.apply {
            title.text = movie.originalTitle
            rating.text = movie.voteAverage.toString()
            logo.loadImage(movie.posterPath)
            setOnClickListener { clickListener(movie) }
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}