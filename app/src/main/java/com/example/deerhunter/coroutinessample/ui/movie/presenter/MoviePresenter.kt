package com.example.deerhunter.coroutinessample.ui.movie.presenter

import com.arellomobile.mvp.InjectViewState

import com.arellomobile.mvp.MvpPresenter
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.interactors.ApiInteractor
import com.example.deerhunter.coroutinessample.ui.movie.view.IMovieView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.jetbrains.anko.coroutines.experimental.bg
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@InjectViewState
class MoviePresenter(private val interactor: ApiInteractor) : MvpPresenter<IMovieView>() {

    private lateinit var movie: Movie

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMovieFullInfo()
    }

    private fun loadMovieFullInfo() = async(UI) {
        try {
            viewState.showProgress()

            // make blocking call to server on Background Thread from Thread Poll (bg {}) and suspend execution of our coroutine till we get the result (await())
            val genreNameList = bg { interactor.loadGenres().genres.filter { it.id in movie.genreIds }.map { it.name } }.await()
            val movieYear = Calendar.getInstance().apply { (SimpleDateFormat("yyyy-MM-dd", Locale("en", "US")).parse(movie.releaseDate))}.get(Calendar.YEAR)
            viewState.showMovieData(movie.title, movieYear, genreNameList, movie.overview, movie.posterPath)

            // simulate long-running operation. Just suspend execution of our coroutine on 2 seconds.
            delay(2, TimeUnit.SECONDS)
            // make blocking call to server on Background Thread from Thread Poll (bg {}) without any suspension
            val similarMoviesNames = bg { interactor.loadSimilarMovies(movie.id).results.map { it.originalTitle } }

            // make blocking call to server on Background Thread from Thread Poll (bg {}) and suspend execution of our coroutine till we get the result (await())
            val movieFullInfo = bg { interactor.loadMovieFullInfo(movie.id) }.await()
            viewState.showAdditionalMovieInfo(movieFullInfo.budget, movieFullInfo.productionCountries.map { it.name }, similarMoviesNames.await())
        } catch (ex: Throwable) {
            viewState.showError(ex.message ?: "exception")
        } finally {
            viewState.hideProgress()
        }
    }

    fun setMovie(movie: Movie) {
        this.movie = movie
    }
}