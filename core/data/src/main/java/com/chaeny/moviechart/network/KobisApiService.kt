package com.chaeny.moviechart.network

import com.chaeny.moviechart.BuildConfig
import com.chaeny.moviechart.model.DailyBoxOfficeResponse
import com.chaeny.moviechart.model.WeeklyBoxOfficeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisApiService {
    @GET("boxoffice/searchDailyBoxOfficeList.json")
    suspend fun getDailyBoxOffice(
        @Query("key") key: String = BuildConfig.KOBIS_API_KEY,
        @Query("targetDt") targetDate: String
    ): DailyBoxOfficeResponse

    @GET("boxoffice/searchWeeklyBoxOfficeList.json")
    suspend fun getWeeklyBoxOffice(
        @Query("key") key: String = BuildConfig.KOBIS_API_KEY,
        @Query("targetDt") targetDate: String,
        @Query("weekGb") weekGb: String = "0"
    ): WeeklyBoxOfficeResponse
}
