package com.example.deerhunter.coroutinessample.di.activity

import com.example.deerhunter.coroutinessample.ui.movie.view.MovieActivity
import com.example.deerhunter.coroutinessample.di.movie.MovieComponent
import com.example.deerhunter.coroutinessample.di.movie.MovieModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun plus(module: MovieModule): MovieComponent

    fun inject(activity: MovieActivity)
}
