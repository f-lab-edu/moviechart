package com.chaeny.moviechart

data class Movie(
    val rank: String,
    val movieId: String,
    val name: String,
    val salesShareRate: String,
    val totalAudience: String,
    val posterUrl: String = ""
)
