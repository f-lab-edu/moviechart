package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie
import com.chaeny.moviechart.TabType
import com.chaeny.moviechart.model.BoxOfficeItem
import com.chaeny.moviechart.network.KobisApiService
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class ApiKobisRepository @Inject constructor(
    private val kobisApiService: KobisApiService
) : KobisRepository {

    override suspend fun getMovies(tabType: TabType): GetMoviesResult {
        return try {
            val targetDate = "20250927"

            val movies = when (tabType) {
                TabType.DAILY -> kobisApiService.getDailyBoxOffice(targetDate = targetDate)
                    .boxOfficeResult.dailyBoxOfficeList
                    .map { it.toMovie() }

                TabType.WEEKLY -> kobisApiService.getWeeklyBoxOffice(targetDate = targetDate)
                    .boxOfficeResult.weeklyBoxOfficeList
                    .map { it.toMovie() }
            }
            when {
                movies.isNotEmpty() -> GetMoviesResult.Success(movies)
                else -> GetMoviesResult.NoResult
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> GetMoviesResult.NoInternet
                is IOException -> GetMoviesResult.NetworkError
                else -> GetMoviesResult.NetworkError
            }
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
