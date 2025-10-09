package com.chaeny.moviechart.repository

import com.chaeny.moviechart.BoxOffice
import com.chaeny.moviechart.TabType
import com.chaeny.moviechart.model.BoxOfficeItem
import com.chaeny.moviechart.network.KobisApiService
import javax.inject.Inject

class ApiKobisRepository @Inject constructor(
    private val kobisApiService: KobisApiService
) : KobisRepository {

    override suspend fun getBoxOfficeList(tabType: TabType): List<BoxOffice> {
        val targetDate = "20250927"

        return when (tabType) {
            TabType.DAILY -> kobisApiService.getDailyBoxOffice(targetDate = targetDate)
                .boxOfficeResult?.dailyBoxOfficeList
                ?.map { it.toBoxOffice() }.orEmpty()

            TabType.WEEKLY -> kobisApiService.getWeeklyBoxOffice(targetDate = targetDate)
                .boxOfficeResult?.weeklyBoxOfficeList
                ?.map { it.toBoxOffice() }.orEmpty()
        }
    }

    private fun BoxOfficeItem.toBoxOffice(): BoxOffice {
        return BoxOffice(
            rank = rank.orEmpty(),
            id = movieCd.orEmpty(),
            name = movieNm.orEmpty(),
            salesShareRate = salesShare.orEmpty(),
            accumulatedAudience = audiAcc.orEmpty()
        )
    }
}
