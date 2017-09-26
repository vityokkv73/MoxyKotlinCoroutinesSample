package com.example.deerhunter.coroutinessample.data

data class MovieListResponse(val page: Int, val totalResults: Int, val totalPages: Int, val results: List<Movie>)