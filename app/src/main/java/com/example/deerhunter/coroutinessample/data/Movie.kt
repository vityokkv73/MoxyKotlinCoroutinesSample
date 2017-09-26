package com.example.deerhunter.coroutinessample.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(val voteCount: Int,
                 val id: Int,
                 val video: Boolean,
                 val voteAverage: Double,
                 val title: String,
                 val popularity: Double,
                 val posterPath: String,
                 val originalLanguage: String,
                 val originalTitle: String,
                 val genreIds: List<Int>,
                 val backdropPath: String,
                 val adult: Boolean,
                 val overview: String,
                 val releaseDate: String): Parcelable