package com.example.deerhunter.coroutinessample.ui.movie.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IMovieView : MvpView {
    fun showError(message: String)
    fun showProgress()
    fun hideProgress()
    fun showMovieData(originalTitle: String, year: Int, genres: List<String>, overviewText: String, posterPath: String)
    fun showAdditionalMovieInfo(budgetText: Int, productionCountriesText: List<String>)
}