package com.example.deerhunter.coroutinessample.ui.movie.presenter

import com.arellomobile.mvp.InjectViewState
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.interactors.ApiInteractor
import com.example.deerhunter.coroutinessample.ui.common.ScopedMvpPresenter
import com.example.deerhunter.coroutinessample.ui.movie.view.IMovieView
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@InjectViewState
class MoviePresenter(private val interactor: ApiInteractor) : ScopedMvpPresenter<IMovieView>() {

    private lateinit var movie: Movie

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        launch { loadMovieFullInfo() }
    }

    private fun loadMovieFullInfo() = coroutineScope {
        try {
            viewState.showProgress()

            // make blocking call to server on Background Thread from Thread Poll (bg {}) and suspend execution of our coroutine till we get the result (await())
            val genreNameList = interactor.loadGenres().await().genres.filter { it.id in movie.genreIds }.map { it.name }
            val movieYear =
                Calendar.getInstance().apply { (SimpleDateFormat("yyyy-MM-dd", Locale("en", "US")).parse(movie.releaseDate)) }.get(Calendar.YEAR)
            viewState.showMovieData(movie.title, movieYear, genreNameList, movie.overview, movie.posterPath)

            // simulate long-running operation. Just suspend execution of our coroutine on 2 seconds.
            delay(2000)
            // make blocking call to server on Background Thread from Thread Poll (bg {}) without any suspension
            val similarMoviesNames = interactor.loadSimilarMovies(movie.id).await().results.map { it.originalTitle }

            // make blocking call to server on Background Thread from Thread Poll (bg {}) and suspend execution of our coroutine till we get the result (await())
            val movieFullInfo = interactor.loadMovieFullInfo(movie.id).await()
            viewState.showAdditionalMovieInfo(movieFullInfo.budget, movieFullInfo.productionCountries.map { it.name }, similarMoviesNames)
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