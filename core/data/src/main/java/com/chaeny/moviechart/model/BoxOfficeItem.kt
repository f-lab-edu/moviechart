package com.chaeny.moviechart.model

import kotlinx.serialization.Serializable

@Serializable
data class BoxOfficeItem(
    val rank: String?,
    val movieCd: String?,
    val movieNm: String?,
    val salesShare: String?,
    val audiAcc: String?
)
