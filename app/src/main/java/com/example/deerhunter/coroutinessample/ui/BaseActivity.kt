package com.example.deerhunter.coroutinessample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.deerhunter.coroutinessample.MobileApplication
import com.example.deerhunter.coroutinessample.di.activity.ActivityComponent
import com.example.deerhunter.coroutinessample.di.activity.ActivityModule

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {
    val activityComponent: ActivityComponent by lazy { (application as MobileApplication).appComponent.plus(ActivityModule(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    abstract fun injectDependencies()
}