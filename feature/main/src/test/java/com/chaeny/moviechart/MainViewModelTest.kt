package com.chaeny.moviechart

import com.chaeny.moviechart.mapper.MovieIdMapper
import com.chaeny.moviechart.repository.KobisRepository
import com.chaeny.moviechart.repository.TmdbRepository
import com.chaeny.moviechart.util.MainCoroutineScopeRule
import io.mockk.coEvery
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

}
