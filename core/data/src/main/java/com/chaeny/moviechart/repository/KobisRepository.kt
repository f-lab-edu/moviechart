package com.chaeny.moviechart.repository

import com.chaeny.moviechart.BoxOffice
import com.chaeny.moviechart.TabType

interface KobisRepository {
    suspend fun getBoxOfficeList(tabType: TabType): List<BoxOffice>
}
