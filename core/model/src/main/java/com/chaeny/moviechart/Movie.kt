package com.chaeny.moviechart

data class Movie(
    val rank: String,
    val id: String,
    val name: String,
    val salesShareRate: String,
    val accumulatedAudience: String,
    val posterUrl: String = ""
)
