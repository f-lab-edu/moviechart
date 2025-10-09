package com.chaeny.moviechart.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbMovieResponse(
    val poster_path: String = ""
)
