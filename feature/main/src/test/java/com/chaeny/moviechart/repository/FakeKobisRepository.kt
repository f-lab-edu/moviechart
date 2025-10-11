package com.chaeny.moviechart.repository

import com.chaeny.moviechart.PeriodType

class FakeKobisRepository : KobisRepository {

    lateinit var moviesResult: GetMoviesResult

    override suspend fun getMovies(periodType: PeriodType): GetMoviesResult {
        return moviesResult
    }
}
