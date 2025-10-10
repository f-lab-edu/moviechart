package com.chaeny.moviechart.repository

import com.chaeny.moviechart.model.TmdbMovieResponse
import com.chaeny.moviechart.network.TmdbApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ApiTmdbRepositoryTest {

    private lateinit var tmdbApiService: TmdbApiService
    private lateinit var repository: ApiTmdbRepository

    @Before
    fun setup() {
        tmdbApiService = mockk()
        repository = ApiTmdbRepository(tmdbApiService)
    }

    @Test
    fun `when getPosterUrl called with valid poster path then full URL should be returned`() = runTest {
        val mockResponse = TmdbMovieResponse(poster_path = "/test1.jpg")
        coEvery { tmdbApiService.getMovieDetails(any()) } returns mockResponse

        val result = repository.getPosterUrl("20243561")
        assertEquals("https://image.tmdb.org/t/p/w500/test1.jpg", result)
    }

    @Test
    fun `when getPosterUrl called with empty poster path then empty string should be returned`() = runTest {
        val mockResponse = TmdbMovieResponse(poster_path = "")
        coEvery { tmdbApiService.getMovieDetails(any()) } returns mockResponse

        val result = repository.getPosterUrl("20243561")
        assertEquals("", result)
    }

    @Test
    fun `when IOException occurs then empty string should be returned`() = runTest {
        coEvery { tmdbApiService.getMovieDetails(any()) } throws IOException()

        val result = repository.getPosterUrl("20243561")
        assertEquals("", result)
    }
}
