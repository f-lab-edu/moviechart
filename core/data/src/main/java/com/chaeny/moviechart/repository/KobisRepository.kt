package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie

interface KobisRepository {
    suspend fun getMovies(): List<Movie>
}
