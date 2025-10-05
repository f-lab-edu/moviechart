package com.chaeny.moviechart.repository

import com.chaeny.moviechart.TabType
import com.chaeny.moviechart.dto.KobisMovie

interface KobisRepository {
    suspend fun getMovies(tabType: TabType): List<KobisMovie>
}
