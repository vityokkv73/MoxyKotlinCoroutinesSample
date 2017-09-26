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
import com.example.deerhunter.coroutinessample.utilities.getActivityComponent
import com.example.deerhunter.coroutinessample.utilities.withArguments
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.android.synthetic.main.movies_fragment.*
import javax.inject.Inject

class MovieFragment : MvpAppCompatFragment(), IMovieView {

//    @Inject
//    @InjectPresenter
//    lateinit var presenter: MoviePresenter
//
//    @ProvidePresenter
//    fun providePresenter(): MoviePresenter {
//        return presenter
//    }

    private val movie: Movie by lazy { arguments.getParcelable<Movie>(MOVIE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivityComponent().plus(MovieModule()).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = movie.originalTitle
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

    companion object {
        private const val MOVIE = "movie"

        fun newInstance(movie: Movie) = MovieFragment().withArguments(MOVIE to movie)
    }
}