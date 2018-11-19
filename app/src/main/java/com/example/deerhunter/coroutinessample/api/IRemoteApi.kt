package com.example.deerhunter.coroutinessample.api

import com.example.deerhunter.coroutinessample.data.GenreListResponse
import com.example.deerhunter.coroutinessample.data.MovieFullInfoResponse
import com.example.deerhunter.coroutinessample.data.MovieListResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRemoteApi {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): Deferred<MovieListResponse>

    @GET("genre/movie/list")
    fun getGenresList(): Deferred<GenreListResponse>

    @GET("movie/{movie_id}")
    fun getMovieFullInfo(@Path("movie_id") id: Int): Deferred<MovieFullInfoResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") id: Int): Deferred<MovieListResponse>
}