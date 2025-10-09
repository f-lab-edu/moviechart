package com.chaeny.moviechart.model

import kotlinx.serialization.Serializable

@Serializable
data class WeeklyBoxOfficeResponse(
    val boxOfficeResult: WeeklyBoxOfficeResult = WeeklyBoxOfficeResult()
)

@Serializable
data class WeeklyBoxOfficeResult(
    val weeklyBoxOfficeList: List<BoxOfficeItem> = emptyList()
)
