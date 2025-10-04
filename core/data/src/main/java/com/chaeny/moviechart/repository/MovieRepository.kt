package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
}
