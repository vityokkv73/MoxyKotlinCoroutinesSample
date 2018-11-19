package com.example.deerhunter.coroutinessample.di

import com.example.deerhunter.coroutinessample.MobileApplication
import com.example.deerhunter.coroutinessample.di.activity.ActivityComponent
import com.example.deerhunter.coroutinessample.di.activity.ActivityModule
import com.example.deerhunter.coroutinessample.di.utilities.AndroidModule
import com.example.deerhunter.coroutinessample.di.utilities.ApiModule
import com.example.deerhunter.coroutinessample.di.utilities.InteractorsModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidModule::class, ApiModule::class, InteractorsModule::class])
interface ApplicationComponent {
    fun plus(activityModule: ActivityModule): ActivityComponent
    fun inject(app: MobileApplication)
}