package com.chaeny.moviechart.repository

import com.chaeny.moviechart.BoxOffice
import com.chaeny.moviechart.TabType
import kotlinx.coroutines.delay
import javax.inject.Inject

class DummyKobisRepository @Inject constructor() : KobisRepository {

    private val dailyData: List<BoxOffice> = listOf(
        BoxOffice("1", "20243561", "어쩔수가없다", "45.3", "833401"),
        BoxOffice("2", "20256757", "극장판 체인소 맨: 레제편", "24.2", "368903"),
        BoxOffice("3", "20253289", "극장판 귀멸의 칼날: 무한성편", "9.2", "4951470"),
        BoxOffice("4", "20242964", "얼굴", "6.7", "873368"),
        BoxOffice("5", "20256526", "브레드이발소: 베이커리타운의 악당들", "4.2", "40141"),
        BoxOffice("6", "20030047", "모노노케 히메", "1.4", "158769"),
        BoxOffice("7", "20254501", "F1 더 무비", "0.6", "5137624"),
        BoxOffice("8", "20256962", "바다 탐험대 옥토넛 어보브 앤 비욘드 : 콰지의 깜짝 어드벤처", "0.9", "6837"),
        BoxOffice("9", "20257095", "명탐정 코난: 17년 전의 진상", "0.8", "67322"),
        BoxOffice("10", "20256864", "프랑켄슈타인 : 더 뮤지컬 라이브", "1.6", "46122")
    )

    private val weeklyData: List<BoxOffice> = listOf(
        BoxOffice("1", "20243561", "어쩔수가없다", "44.7", "1073654"),
        BoxOffice("2", "20256757", "극장판 체인소 맨: 레제편", "21.5", "474471"),
        BoxOffice("3", "20253289", "극장판 귀멸의 칼날: 무한성편", "8.9", "4996308"),
        BoxOffice("4", "20242964", "얼굴", "7.8", "907388"),
        BoxOffice("5", "20256526", "브레드이발소: 베이커리타운의 악당들", "2.3", "72573"),
        BoxOffice("6", "20030047", "모노노케 히메", "1.6", "164797"),
        BoxOffice("7", "20254501", "F1 더 무비", "1.0", "5142920"),
        BoxOffice("8", "20257095", "명탐정 코난: 17년 전의 진상", "1.1", "71601"),
        BoxOffice("9", "20256864", "프랑켄슈타인 : 더 뮤지컬 라이브", "1.9", "49303"),
        BoxOffice("10", "20233039", "살인자 리포트", "0.5", "364379")
    )

    override suspend fun getBoxOfficeList(tabType: TabType): List<BoxOffice> {
        delay(2000)
        return when (tabType) {
            TabType.DAILY -> dailyData
            TabType.WEEKLY -> weeklyData
        }
    }
}
