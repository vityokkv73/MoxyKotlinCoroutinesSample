package com.example.deerhunter.coroutinessample.interactors

import com.example.deerhunter.coroutinessample.api.IRemoteApi

class ApiInteractor(private val api: IRemoteApi) {

    fun loadMovies(offset: Int = 0, limit: Int = MOVIES_LIMIT) = api.getTopRatedMovies(offset, limit)

    companion object {
        private const val MOVIES_LIMIT = 100
    }
}
