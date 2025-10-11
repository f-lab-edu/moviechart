package com.chaeny.moviechart

import com.chaeny.moviechart.mapper.MovieIdMapper
import com.chaeny.moviechart.repository.FakeKobisRepository
import com.chaeny.moviechart.repository.FakeTmdbRepository
import com.chaeny.moviechart.repository.GetMoviesResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMoviesWithPostersUseCaseTest {

    private lateinit var useCase: GetMoviesWithPostersUseCase
    private lateinit var kobisRepository: FakeKobisRepository
    private lateinit var tmdbRepository: FakeTmdbRepository
    private lateinit var movieIdMapper: MovieIdMapper

    @Before
    fun setup() {
        kobisRepository = FakeKobisRepository()
        tmdbRepository = FakeTmdbRepository()
        movieIdMapper = MovieIdMapper()
        useCase = GetMoviesWithPostersUseCase(kobisRepository, tmdbRepository, movieIdMapper)
    }

    @Test
    fun `when useCase called then success with posters should be returned`() = runTest {
        val result = useCase(PeriodType.DAILY)

        val expectedMovies = listOf(
            Movie("1", "20243561", "어쩔수가없다", "45.3", "833401", "https://image.tmdb.org/t/p/w500/test1.jpg"),
            Movie("2", "20256757", "극장판 체인소 맨: 레제편", "24.2", "368903", "https://image.tmdb.org/t/p/w500/test2.jpg")
        )
        assertEquals(GetMoviesResult.Success(expectedMovies), result)
    }
}
