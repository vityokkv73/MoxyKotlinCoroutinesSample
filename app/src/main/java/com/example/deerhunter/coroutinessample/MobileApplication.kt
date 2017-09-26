package com.example.deerhunter.coroutinessample

import android.app.Application
import com.example.deerhunter.coroutinessample.di.ApplicationComponent
import com.example.deerhunter.coroutinessample.di.DaggerApplicationComponent
import com.example.deerhunter.coroutinessample.di.utilities.AndroidModule
import timber.log.Timber


class MobileApplication : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
    }


    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        Timber.plant(Timber.DebugTree())
    }
}