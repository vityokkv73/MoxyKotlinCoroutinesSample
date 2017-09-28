package com.example.deerhunter.coroutinessample.ui.movie.view

import android.annotation.SuppressLint
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
import com.example.deerhunter.coroutinessample.ui.movie.presenter.MoviePresenter
import com.example.deerhunter.coroutinessample.utilities.*
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.movie_fragment.*
import javax.inject.Inject

class MovieFragment : MvpAppCompatFragment(), IMovieView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MoviePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

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
        presenter.setMovie(movie)
    }

    override fun showError(message: String) {
        Toasty.error(activity, message).show()
    }

    override fun showProgress() {
        progressBar.makeVisible()
    }

    override fun hideProgress() {
        progressBar.makeGone()
    }

    @SuppressLint("SetTextI18n")
    override fun showMovieData(originalTitle: String, year: Int, genres: List<String>, overviewText: String, posterPath: String) {
        title.text = originalTitle
        yearAndGenres.text = "$year, ${genres.joinToString()}"
        overview.text = overviewText
        logo.loadImage(posterPath)
    }

    override fun showAdditionalMovieInfo(budgetText: Int, productionCountriesText: List<String>, similarMoviesNames: List<String>) {
        budget.makeVisible()
        productionCountries.makeVisible()
        similarMovies.makeVisible()
        budget.text = getString(R.string.budget, budgetText)
        productionCountries.text = getString(R.string.production_countries, productionCountriesText.joinToString())
        similarMovies.text = getString(R.string.similar_movies, similarMoviesNames.joinToString())
    }

    companion object {
        private const val MOVIE = "movie"

        fun newInstance(movie: Movie) = MovieFragment().withArguments(MOVIE to movie)
    }
}