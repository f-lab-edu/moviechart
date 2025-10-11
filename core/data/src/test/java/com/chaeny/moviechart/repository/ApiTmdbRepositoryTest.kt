package com.chaeny.moviechart.repository

import com.chaeny.moviechart.model.TmdbMovieResponse
import com.chaeny.moviechart.network.FakeTmdbApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ApiTmdbRepositoryTest {

    private lateinit var tmdbApiService: FakeTmdbApiService
    private lateinit var repository: ApiTmdbRepository

    @Before
    fun setup() {
        tmdbApiService = FakeTmdbApiService()
        repository = ApiTmdbRepository(tmdbApiService)
    }

    @Test
    fun `when getPosterUrl called with valid poster path then full URL should be returned`() = runTest {
        tmdbApiService.response = TmdbMovieResponse(poster_path = "/test1.jpg")

        val result = repository.getPosterUrl("20243561")
        assertEquals("https://image.tmdb.org/t/p/w500/test1.jpg", result)
    }

    @Test
    fun `when getPosterUrl called with empty poster path then empty string should be returned`() = runTest {
        tmdbApiService.response = TmdbMovieResponse(poster_path = "")

        val result = repository.getPosterUrl("20243561")
        assertEquals("", result)
    }

    @Test
    fun `when IOException occurs then empty string should be returned`() = runTest {
        tmdbApiService.throwException = true
        tmdbApiService.exception = IOException()

        val result = repository.getPosterUrl("20243561")
        assertEquals("", result)
    }
}
