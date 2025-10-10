package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie
import com.chaeny.moviechart.TabType
import com.chaeny.moviechart.model.BoxOfficeItem
import com.chaeny.moviechart.model.DailyBoxOfficeResponse
import com.chaeny.moviechart.model.DailyBoxOfficeResult
import com.chaeny.moviechart.network.FakeKobisApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ApiKobisRepositoryTest {

    private lateinit var kobisApiService: FakeKobisApiService
    private lateinit var repository: ApiKobisRepository

    @Before
    fun setup() {
        kobisApiService = FakeKobisApiService()
        repository = ApiKobisRepository(kobisApiService)
    }

    @Test
    fun `when getMovies with DAILY type called then Success with movie list should be returned`() = runTest {
        val mockBoxOfficeItems = TEST_RESPONSE_LIST
        val mockResponse = DailyBoxOfficeResponse(DailyBoxOfficeResult(mockBoxOfficeItems))
        kobisApiService.dailyBoxOfficeResponse = mockResponse

        val result = repository.getMovies(TabType.DAILY)
        val expectedResult = GetMoviesResult.Success(TEST_MOVIE_LIST)
        assertEquals(expectedResult, result)
    }

    companion object {
        private val TEST_RESPONSE_LIST = listOf(
            BoxOfficeItem(
                rank = "1",
                movieCd = "20243561",
                movieNm = "어쩔수가없다",
                salesShare = "45.3",
                audiAcc = "833401"
            ),
            BoxOfficeItem(
                rank = "2",
                movieCd = "20256757",
                movieNm = "극장판 체인소 맨: 레제편",
                salesShare = "24.2",
                audiAcc = "368903"
            )
        )
        private val TEST_MOVIE_LIST = listOf(
            Movie(
                rank = "1",
                id = "20243561",
                name = "어쩔수가없다",
                salesShareRate = "45.3",
                accumulatedAudience = "833401"
            ),
            Movie(
                rank = "2",
                id = "20256757",
                name = "극장판 체인소 맨: 레제편",
                salesShareRate = "24.2",
                accumulatedAudience = "368903"
            )
        )
    }
}
