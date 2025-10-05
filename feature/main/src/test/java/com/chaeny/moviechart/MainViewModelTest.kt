package com.chaeny.moviechart

import com.chaeny.moviechart.dto.KobisMovie
import com.chaeny.moviechart.mapper.MovieIdMapper
import com.chaeny.moviechart.repository.KobisRepository
import com.chaeny.moviechart.repository.TmdbRepository
import com.chaeny.moviechart.util.MainCoroutineScopeRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineScopeRule()

    private lateinit var kobisRepository: KobisRepository
    private lateinit var tmdbRepository: TmdbRepository
    private lateinit var movieIdMapper: MovieIdMapper

    @Before
    fun setup() {
        kobisRepository = mockk()
        tmdbRepository = mockk()
        movieIdMapper = MovieIdMapper()
    }

    @Test
    fun `when viewModel created then selectedTab should be DAILY`() {
        coEvery { kobisRepository.getMovies(any()) } returns emptyList()

        val viewModel = MainViewModel(kobisRepository, tmdbRepository, movieIdMapper)

        assertEquals(TabType.DAILY, viewModel.selectedTab.value)
    }

    @Test
    fun `when data loading completes then isLoading should be false`() {
        coEvery { kobisRepository.getMovies(any()) } returns TEST_KOBIS_MOVIES
        coEvery { tmdbRepository.getPosterUrl(any()) } returns ""

        val viewModel = MainViewModel(kobisRepository, tmdbRepository, movieIdMapper)

        coVerify { kobisRepository.getMovies(any()) }
        coVerify { tmdbRepository.getPosterUrl(any()) }
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `when onTabSelected WEEKLY then selectedTab should be WEEKLY and repository called`() {
        coEvery { kobisRepository.getMovies(any()) } returns emptyList()
        val viewModel = MainViewModel(kobisRepository, tmdbRepository, movieIdMapper)

        viewModel.onTabSelected(TabType.WEEKLY)

        assertEquals(TabType.WEEKLY, viewModel.selectedTab.value)
        coVerify { kobisRepository.getMovies(TabType.WEEKLY) }
    }

    companion object {
        private val TEST_KOBIS_MOVIES = listOf(
            KobisMovie(
                rank = "1",
                id = "20243561",
                name = "어쩔수가없다",
                salesShareRate = "45.3",
                accumulatedAudience = "833401"
            ),
            KobisMovie(
                rank = "2",
                id = "20256757",
                name = "극장판 체인소 맨: 레제편",
                salesShareRate = "24.2",
                accumulatedAudience = "368903"
            )
        )
    }

}
