package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie
import com.chaeny.moviechart.PeriodType

class FakeKobisRepository : KobisRepository {

    override suspend fun getMovies(periodType: PeriodType): GetMoviesResult {
        val movies = listOf(
            Movie("1", "20243561", "어쩔수가없다", "45.3", "833401"),
            Movie("2", "20256757", "극장판 체인소 맨: 레제편", "24.2", "368903")
        )
        return GetMoviesResult.Success(movies)
    }
}
