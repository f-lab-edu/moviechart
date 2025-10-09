package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie
import com.chaeny.moviechart.TabType

interface KobisRepository {
    suspend fun getMovies(tabType: TabType): List<Movie>
}
