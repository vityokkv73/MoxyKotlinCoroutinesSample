package com.example.deerhunter.coroutinessample.di.movie

import com.example.deerhunter.coroutinessample.di.FragmentScope
import com.example.deerhunter.coroutinessample.interactors.ApiInteractor
import com.example.deerhunter.coroutinessample.ui.movie.presenter.MoviePresenter
import com.example.deerhunter.coroutinessample.ui.movie.presenter.MoviesPresenter
import dagger.Module
import dagger.Provides

@Module
class MovieModule {

    @Provides
    @FragmentScope
    internal fun provideMoviesPresenter(apiInteractor: ApiInteractor): MoviesPresenter {
        return MoviesPresenter(apiInteractor)
    }

    @Provides
    @FragmentScope
    internal fun provideMoviePresenter(apiInteractor: ApiInteractor): MoviePresenter {
        return MoviePresenter(apiInteractor)
    }
}