package com.example.deerhunter.coroutinessample.ui.movie.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.deerhunter.coroutinessample.data.Movie
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import java.util.*

class MoviesAdapter(context: Context, listener: (Movie) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegates: AdapterDelegatesManager<List<Movie>> = AdapterDelegatesManager()

    private var items = ArrayList<Movie>()

    init {
        delegates.addDelegate(MovieAdapterDelegate(context, listener))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates.onBindViewHolder(items, position, holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return delegates.getItemViewType(items, position)
    }

    fun addMovies(data: List<Movie>) {
        items.addAll(data)
        notifyDataSetChanged()
    }
}
