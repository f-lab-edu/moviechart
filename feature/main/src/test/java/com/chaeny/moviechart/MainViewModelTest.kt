package com.chaeny.moviechart

import com.chaeny.moviechart.repository.GetMoviesResult
import com.chaeny.moviechart.usecase.FakeGetMoviesWithPostersUseCase
import com.chaeny.moviechart.util.MainCoroutineScopeRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineScopeRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var useCase: FakeGetMoviesWithPostersUseCase

    @Before
    fun setup() {
        useCase = FakeGetMoviesWithPostersUseCase()
    }

    @Test
    fun `when viewModel created then selectedType should be DAILY`() {
        viewModel = MainViewModel(useCase)

        assertEquals(PeriodType.DAILY, viewModel.selectedType.value)
    }

    @Test
    fun `when onTypeSelected WEEKLY then selectedType should be WEEKLY`() {
        viewModel = MainViewModel(useCase)
        viewModel.onTypeSelected(PeriodType.WEEKLY)

        assertEquals(PeriodType.WEEKLY, viewModel.selectedType.value)
    }

    @Test
    fun `when loading movies then isLoading should be true and false after completion`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        viewModel = MainViewModel(useCase)

        assertEquals(true, viewModel.isLoading.value)
        advanceUntilIdle()
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `when useCase returns NoResult then movies should be empty`() {
        useCase.result = GetMoviesResult.NoResult
        viewModel = MainViewModel(useCase)

        assertEquals(emptyList<Movie>(), viewModel.movies.value)
    }

    @Test
    fun `when same type is selected again then useCase should not be called`() {
        viewModel = MainViewModel(useCase)
        viewModel.onTypeSelected(PeriodType.DAILY)

        val expectedCount = 1
        assertEquals(expectedCount, useCase.callCount)
    }

    @Test
    fun `when switching between types then movies should update correctly`() {
        val dailyMovies = listOf(
            Movie("1", "20243561", "어쩔수가없다", "45.3", "833401", "daily.jpg"),
        )
        val weeklyMovies = listOf(
            Movie("1", "20256757", "극장판 체인소 맨: 레제편", "24.2", "368903", "weekly.jpg")
        )
        useCase.resultByType = mapOf(
            PeriodType.DAILY to GetMoviesResult.Success(dailyMovies),
            PeriodType.WEEKLY to GetMoviesResult.Success(weeklyMovies)
        )

        viewModel = MainViewModel(useCase)
        assertEquals(dailyMovies, viewModel.movies.value)

        viewModel.onTypeSelected(PeriodType.WEEKLY)
        assertEquals(weeklyMovies, viewModel.movies.value)
    }
}
