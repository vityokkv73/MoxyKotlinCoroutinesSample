package com.example.deerhunter.coroutinessample.ui.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.deerhunter.coroutinessample.R
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.di.movie.MovieModule
import com.example.deerhunter.coroutinessample.ui.movie.presenter.MoviesPresenter
import com.example.deerhunter.coroutinessample.ui.movie.view.adapter.MoviesAdapter
import javax.inject.Inject
import com.example.deerhunter.coroutinessample.utilities.getActivityComponent
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.movies_fragment.*
import android.support.v7.widget.GridLayoutManager


class MoviesFragment : MvpAppCompatFragment(), IMoviesView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MoviesPresenter

    @ProvidePresenter
    fun providePresenter(): MoviesPresenter {
        return presenter
    }

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivityComponent().plus(MovieModule()).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MoviesAdapter(activity, { presenter.movieClicked(it) })
        with(moviesList) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = moviesAdapter
        }
        presenter.loadMovies()
    }

    override fun showError(message: String) {
        Toasty.error(activity, message).show()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun addMovies(movies: List<Movie>) {
        moviesAdapter.addMovies(movies)
    }

    override fun openMovieScreen(movie: Movie) {
        activity.supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, MovieFragment.newInstance(movie)).addToBackStack(null).commit()
    }
}