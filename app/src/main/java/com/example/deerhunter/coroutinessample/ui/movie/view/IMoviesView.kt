package com.example.deerhunter.coroutinessample.ui.movie.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.deerhunter.coroutinessample.data.Movie

@StateStrategyType(AddToEndSingleStrategy::class)
interface IMoviesView : MvpView {
    fun showError(message: String)
    fun showProgress()
    fun hideProgress()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openMovieScreen(movie: Movie)

    @StateStrategyType(AddToEndStrategy::class)
    fun addMovies(movies: List<Movie>)
}