package com.chaeny.moviechart.mapper

import javax.inject.Inject

class MovieIdMapper @Inject constructor() {

    private val kobisToTmdbMap = mapOf(
        "20243561" to "639988",   // 어쩔수가없다
        "20256757" to "1218925",  // 극장판 체인소 맨: 레제편
        "20253289" to "1311031",  // 극장판 귀멸의 칼날: 무한성편
        "20242964" to "1316719",  // 얼굴
        "20256526" to "1521915",  // 브레드이발소: 베이커리타운의 악당들
        "20030047" to "128",      // 모노노케 히메
        "20254501" to "911430",   // F1 더 무비
        "20256962" to "1228018",  // 바다 탐험대 옥토넛 어보브 앤 비욘드 : 콰지의 깜짝 어드벤처
        "20257095" to "1540229",  // 명탐정 코난: 17년 전의 진상
        "20256864" to "1519318",  // 프랑켄슈타인 : 더 뮤지컬 라이브
        "20233039" to "1151766"   // 살인자 리포트
    )

    fun getTmdbId(kobisMovieId: String): String {
        return kobisToTmdbMap[kobisMovieId] ?: ""
    }
}
