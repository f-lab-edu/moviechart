package com.chaeny.moviechart.repository

import com.chaeny.moviechart.dto.KobisMovie

interface KobisRepository {
    suspend fun getMovies(): List<KobisMovie>
}
