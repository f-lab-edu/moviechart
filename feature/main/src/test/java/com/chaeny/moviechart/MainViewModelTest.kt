package com.chaeny.moviechart

import com.chaeny.moviechart.repository.GetMoviesResult
import com.chaeny.moviechart.util.MainCoroutineScopeRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
    private lateinit var useCase: GetMoviesWithPostersUseCase

    @Before
    fun setup() {
        useCase = mockk()
    }

    private fun createViewModel(movieList: List<Movie>): MainViewModel {
        stubUseCase(movieList)
        return MainViewModel(useCase)
    }

    private fun stubUseCase(movieList: List<Movie>) {
        coEvery { useCase(any()) } returns GetMoviesResult.Success(movieList)
    }

    @Test
    fun `when viewModel created then selectedType should be DAILY and useCase called with DAILY`() {
        viewModel = createViewModel(TEST_MOVIE_LIST)

        assertEquals(PeriodType.DAILY, viewModel.selectedType.value)
        coVerify { useCase(PeriodType.DAILY) }
    }

    @Test
    fun `when data loading completes then isLoading should be false`() {
        viewModel = createViewModel(TEST_MOVIE_LIST)

        coVerify { useCase(any()) }
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `when movies are loading then isLoading should be true and false after completion`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        viewModel = createViewModel(TEST_MOVIE_LIST)

        assertEquals(true, viewModel.isLoading.value)
        advanceUntilIdle()
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `when onTypeSelected WEEKLY then selectedType should be WEEKLY and useCase called`() {
        viewModel = createViewModel(TEST_MOVIE_LIST)
        viewModel.onTypeSelected(PeriodType.WEEKLY)

        assertEquals(PeriodType.WEEKLY, viewModel.selectedType.value)
        coVerify { useCase(PeriodType.WEEKLY) }
    }

    @Test
    fun `when useCase returns empty list then movies should be empty`() {
        viewModel = createViewModel(emptyList())

        assertEquals(emptyList<Movie>(), viewModel.movies.value)
        coVerify(exactly = 1) { useCase(any()) }
    }

    @Test
    fun `when switching between types then movies should update correctly`() {
        val dailyMovies = listOf(Movie("1", "20243561", "어쩔수가없다", "45.3", "833401", "daily.jpg"))
        val weeklyMovies = listOf(Movie("1", "20242964", "얼굴", "30.0", "500000", "weekly.jpg"))
        coEvery { useCase(PeriodType.DAILY) } returns GetMoviesResult.Success(dailyMovies)
        coEvery { useCase(PeriodType.WEEKLY) } returns GetMoviesResult.Success(weeklyMovies)

        viewModel = MainViewModel(useCase)
        assertEquals(dailyMovies, viewModel.movies.value)

        viewModel.onTypeSelected(PeriodType.WEEKLY)
        assertEquals(weeklyMovies, viewModel.movies.value)

        viewModel.onTypeSelected(PeriodType.DAILY)
        assertEquals(dailyMovies, viewModel.movies.value)
    }

    @Test
    fun `when same type is selected again then useCase should not be called`() {
        viewModel = createViewModel(TEST_MOVIE_LIST)

        viewModel.onTypeSelected(PeriodType.DAILY)
        coVerify(exactly = 1) { useCase(PeriodType.DAILY) }
    }

    companion object {
        private val TEST_MOVIE_LIST = listOf(
            Movie(
                rank = "1",
                id = "20243561",
                name = "어쩔수가없다",
                salesShareRate = "45.3",
                accumulatedAudience = "833401",
                posterUrl = "test1.jpg"
            ),
            Movie(
                rank = "2",
                id = "20256757",
                name = "극장판 체인소 맨: 레제편",
                salesShareRate = "24.2",
                accumulatedAudience = "368903",
                posterUrl = "test2.jpg"
            )
        )
    }
}
