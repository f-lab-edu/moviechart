package com.chaeny.moviechart.repository

import com.chaeny.moviechart.KobisMovie
import com.chaeny.moviechart.TabType
import com.chaeny.moviechart.model.BoxOfficeItem
import com.chaeny.moviechart.network.KobisApiService
import javax.inject.Inject

class ApiKobisRepository @Inject constructor(
    private val kobisApiService: KobisApiService
) : KobisRepository {

    override suspend fun getBoxOfficeList(tabType: TabType): List<KobisMovie> {
        val targetDate = "20250927"

        return when (tabType) {
            TabType.DAILY -> kobisApiService.getDailyBoxOffice(targetDate = targetDate)
                .boxOfficeResult?.dailyBoxOfficeList
                ?.map { it.toKobisMovie() }.orEmpty()

            TabType.WEEKLY -> kobisApiService.getWeeklyBoxOffice(targetDate = targetDate)
                .boxOfficeResult?.weeklyBoxOfficeList
                ?.map { it.toKobisMovie() }.orEmpty()
        }
    }

    private fun BoxOfficeItem.toKobisMovie(): KobisMovie {
        return KobisMovie(
            rank = rank.orEmpty(),
            id = movieCd.orEmpty(),
            name = movieNm.orEmpty(),
            salesShareRate = salesShare.orEmpty(),
            accumulatedAudience = audiAcc.orEmpty()
        )
    }
}
