package com.example.deerhunter.coroutinessample.ui.movie.view

import android.os.Bundle

import com.example.deerhunter.coroutinessample.R
import com.example.deerhunter.coroutinessample.ui.BaseActivity

class MovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)
    }

    override fun injectDependencies() {
        activityComponent.inject(this)
    }
}
