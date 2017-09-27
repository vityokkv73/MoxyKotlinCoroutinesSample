package com.example.deerhunter.coroutinessample.api

import com.example.deerhunter.coroutinessample.data.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IRemoteApi {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): MovieListResponse
}