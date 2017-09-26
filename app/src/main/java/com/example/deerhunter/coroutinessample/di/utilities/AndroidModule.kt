package com.example.deerhunter.coroutinessample.di.utilities

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    internal fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
