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
            val genreNameList = bg { interactor.loadGenres().genres }.await().filter { it.id in movie.genreIds }.map { it.name }
            val movieYear = Calendar.getInstance().apply { (SimpleDateFormat("yyyy-MM-dd", Locale("en", "US")).parse(movie.releaseDate))}.get(Calendar.YEAR)
            viewState.showMovieData(movie.title, movieYear, genreNameList, movie.overview, movie.posterPath)
            // simulate long-running operation
            delay(2, TimeUnit.SECONDS)
            val movieFulInfo = bg { interactor.loadMovieFullInfo(movie.id) }.await()
            viewState.showAdditionalMovieInfo(movieFulInfo.budget, movieFulInfo.productionCountries.map { it.name })
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