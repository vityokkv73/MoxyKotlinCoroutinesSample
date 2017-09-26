package com.example.deerhunter.coroutinessample.di.utilities

import com.example.deerhunter.coroutinessample.api.IRemoteApi
import com.example.deerhunter.coroutinessample.interactors.ApiInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorsModule {

    @Provides
    @Singleton
    internal fun provideApiInteractor(remoteApi: IRemoteApi): ApiInteractor {
        return ApiInteractor(remoteApi)
    }
}