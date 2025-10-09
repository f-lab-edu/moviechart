package com.chaeny.moviechart.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyBoxOfficeResponse(
    val boxOfficeResult: DailyBoxOfficeResult?
)

@Serializable
data class DailyBoxOfficeResult(
    val dailyBoxOfficeList: List<BoxOfficeItem>?
)
