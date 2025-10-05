package com.chaeny.moviechart.repository

interface TmdbRepository {
    suspend fun getPosterUrl(movieId: String): String
}
