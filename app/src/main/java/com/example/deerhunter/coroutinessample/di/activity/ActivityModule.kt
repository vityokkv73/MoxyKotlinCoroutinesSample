package com.example.deerhunter.coroutinessample.di.activity

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    fun provideActivity(): AppCompatActivity {
        return activity
    }
}
