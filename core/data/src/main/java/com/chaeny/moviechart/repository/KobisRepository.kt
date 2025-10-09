package com.chaeny.moviechart.repository

import com.chaeny.moviechart.KobisMovie
import com.chaeny.moviechart.TabType

interface KobisRepository {
    suspend fun getBoxOfficeList(tabType: TabType): List<KobisMovie>
}
