package com.example.deerhunter.coroutinessample.di.movie

import com.example.deerhunter.coroutinessample.di.FragmentScope
import com.example.deerhunter.coroutinessample.ui.movie.view.MovieFragment
import com.example.deerhunter.coroutinessample.ui.movie.view.MoviesFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = arrayOf(MovieModule::class))
interface MovieComponent {
    fun inject(fragment: MoviesFragment)
    fun inject(fragment: MovieFragment)
}