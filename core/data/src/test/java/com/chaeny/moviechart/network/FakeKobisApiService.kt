package com.chaeny.moviechart.network

import com.chaeny.moviechart.model.DailyBoxOfficeResponse
import com.chaeny.moviechart.model.WeeklyBoxOfficeResponse

class FakeKobisApiService : KobisApiService {

    var dailyBoxOfficeResponse = DailyBoxOfficeResponse()
    var weeklyBoxOfficeResponse = WeeklyBoxOfficeResponse()

    override suspend fun getDailyBoxOffice(
        key: String,
        targetDate: String
    ): DailyBoxOfficeResponse {
        return dailyBoxOfficeResponse
    }

    override suspend fun getWeeklyBoxOffice(
        key: String,
        targetDate: String,
        weekGb: String
    ): WeeklyBoxOfficeResponse {
        return weeklyBoxOfficeResponse
    }
}
