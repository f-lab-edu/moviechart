package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie
import com.chaeny.moviechart.TabType
import com.chaeny.moviechart.model.BoxOfficeItem
import com.chaeny.moviechart.network.KobisApiService
import javax.inject.Inject

class ApiKobisRepository @Inject constructor(
    private val kobisApiService: KobisApiService
) : KobisRepository {

    override suspend fun getMovies(tabType: TabType): List<Movie> {
        val targetDate = "20250927"

        return when (tabType) {
            TabType.DAILY -> kobisApiService.getDailyBoxOffice(targetDate = targetDate)
                .boxOfficeResult.dailyBoxOfficeList
                .map { it.toMovie() }

            TabType.WEEKLY -> kobisApiService.getWeeklyBoxOffice(targetDate = targetDate)
                .boxOfficeResult.weeklyBoxOfficeList
                .map { it.toMovie() }
        }
    }

    private fun BoxOfficeItem.toMovie(): Movie {
        return Movie(
            rank = rank,
            id = movieCd,
            name = movieNm,
            salesShareRate = salesShare,
            accumulatedAudience = audiAcc
        )
    }
}
