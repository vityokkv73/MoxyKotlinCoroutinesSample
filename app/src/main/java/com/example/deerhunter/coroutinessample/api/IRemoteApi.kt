package com.example.deerhunter.coroutinessample.api

import com.example.deerhunter.coroutinessample.data.GenreListResponse
import com.example.deerhunter.coroutinessample.data.MovieFullInfoResponse
import com.example.deerhunter.coroutinessample.data.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRemoteApi {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): MovieListResponse

    @GET("genre/movie/list")
    fun getGenresList(): GenreListResponse

    @GET("movie/{movie_id}")
    fun getMovieFullInfo(@Path("movie_id") id: Int): MovieFullInfoResponse

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") id: Int): MovieListResponse
}