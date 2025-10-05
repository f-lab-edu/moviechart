package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie
import kotlinx.coroutines.delay
import javax.inject.Inject

class DummyKobisRepository @Inject constructor() : KobisRepository {

    private val dummyData: List<Movie> = listOf(
        Movie("1", "20243561", "어쩔수가없다", "45.3", "833401"),
        Movie("2", "20256757", "극장판 체인소 맨: 레제편", "24.2", "368903"),
        Movie("3", "20253289", "극장판 귀멸의 칼날: 무한성편", "9.2", "4951470"),
        Movie("4", "20242964", "얼굴", "6.7", "873368"),
        Movie("5", "20256526", "브레드이발소: 베이커리타운의 악당들", "4.2", "40141"),
        Movie("6", "20030047", "모노노케 히메", "1.4", "158769"),
        Movie("7", "20254501", "F1 더 무비", "0.6", "5137624"),
        Movie("8", "20256962", "바다 탐험대 옥토넛 어보브 앤 비욘드 : 콰지의 깜짝 어드벤처", "0.9", "6837"),
        Movie("9", "20257095", "명탐정 코난: 17년 전의 진상", "0.8", "67322"),
        Movie("10", "20256864", "프랑켄슈타인 : 더 뮤지컬 라이브", "1.6", "46122")
    )

    override suspend fun getMovies(): List<Movie> {
        delay(2000)
        return dummyData
    }
}
