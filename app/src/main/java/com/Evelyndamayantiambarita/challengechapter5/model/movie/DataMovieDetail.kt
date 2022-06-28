package com.Evelyndamayantiambarita.challengechapter5.model.movie

data class DataMovieDetail(
    val id: Int,
    val image: String,
    val title: String,
    val vote_average: Double,
    val overview: String?
)