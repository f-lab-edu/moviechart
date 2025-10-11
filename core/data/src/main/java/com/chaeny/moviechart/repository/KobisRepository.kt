package com.chaeny.moviechart.repository

import com.chaeny.moviechart.PeriodType

interface KobisRepository {
    suspend fun getMovies(periodType: PeriodType): GetMoviesResult
}
