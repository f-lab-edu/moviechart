package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie
import javax.inject.Inject
import kotlinx.coroutines.delay

class DummyMovieRepository @Inject constructor() : MovieRepository {

    private val dummyData: List<Movie> = listOf(
        Movie("1", "어쩔수가없다", "https://image.tmdb.org/t/p/w500/pf7vZxoLYtLQ366VNlGrjBxwL7A.jpg", "45.3", "833401"),
        Movie("2", "극장판 체인소 맨: 레제편", "https://image.tmdb.org/t/p/w500/Amu0HNWfpxo2ZaulueNVxDLADz8.jpg", "24.2", "368903"),
        Movie("3", "극장판 귀멸의 칼날: 무한성편", "https://image.tmdb.org/t/p/w500/m6Dho6hDCcL5KI8mOQNemZAedFI.jpg", "9.2", "4951470"),
        Movie("4", "얼굴", "https://image.tmdb.org/t/p/w500/9RsHtbUMXMfHjkL74BhM7KFEozT.jpg", "6.7", "873368"),
        Movie("5", "브레드이발소: 베이커리타운의 악당들", "https://image.tmdb.org/t/p/w500/cwvehMf8bnWwUhKOFR6qHTxg1VO.jpg", "4.2", "40141"),
        Movie("6", "모노노케 히메", "https://image.tmdb.org/t/p/w500/gEVSN7rzQsypG4YfYObsPmMtYpP.jpg", "1.4", "158769"),
        Movie("7", "F1 더 무비", "https://image.tmdb.org/t/p/w500/bvVoP1t2gNvmE9ccSrqR1zcGHGM.jpg", "0.6", "5137624"),
        Movie("8", "바다 탐험대 옥토넛 어보브 앤 비욘드 : 콰지의 깜짝 어드벤처", "https://image.tmdb.org/t/p/w500/eSNprN73xK9LKN8f5y5Ee446QzK.jpg", "0.9", "6837"),
        Movie("9", "명탐정 코난: 17년 전의 진상", "https://image.tmdb.org/t/p/w500/kI9ffyOEwj0bdpttdPEtAVDFHxC.jpg", "0.8", "67322"),
        Movie("10", "프랑켄슈타인 : 더 뮤지컬 라이브", "https://image.tmdb.org/t/p/w500/rXyniH1Xyp3xksHzZ0wSU6IqDjh.jpg", "1.6", "46122")
    )

    override suspend fun getMovies(): List<Movie> {
        delay(3000)
        return dummyData
    }
}
