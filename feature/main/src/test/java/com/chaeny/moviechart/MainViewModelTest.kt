package com.chaeny.moviechart

import com.chaeny.moviechart.dto.KobisMovie
import com.chaeny.moviechart.mapper.MovieIdMapper
import com.chaeny.moviechart.repository.KobisRepository
import com.chaeny.moviechart.repository.TmdbRepository
import com.chaeny.moviechart.util.MainCoroutineScopeRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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
    private lateinit var kobisRepository: KobisRepository
    private lateinit var tmdbRepository: TmdbRepository
    private lateinit var movieIdMapper: MovieIdMapper

    @Before
    fun setup() {
        kobisRepository = mockk()
        tmdbRepository = mockk()
        movieIdMapper = spyk(MovieIdMapper())
    }

    private fun createViewModel(
        kobisMovies: List<KobisMovie>,
        posterUrls: Map<String, String>
    ): MainViewModel {
        stubKobisRepository(kobisMovies)
        stubTmdbRepository(posterUrls)
        return MainViewModel(kobisRepository, tmdbRepository, movieIdMapper)
    }

    private fun stubKobisRepository(kobisMovies: List<KobisMovie>) {
        coEvery { kobisRepository.getMovies(any()) } returns kobisMovies
    }

    private fun stubTmdbRepository(posterUrls: Map<String, String>) {
        posterUrls.forEach { (movieId, url) ->
            coEvery { tmdbRepository.getPosterUrl(movieId) } returns url
        }
    }

    @Test
    fun `when viewModel created then selectedTab should be DAILY and repository called with DAILY`() {
        viewModel = createViewModel(TEST_KOBIS_MOVIES, TEST_POSTER_URLS)

        assertEquals(TabType.DAILY, viewModel.selectedTab.value)
        coVerify { kobisRepository.getMovies(TabType.DAILY) }
    }

    @Test
    fun `when data loading completes then isLoading should be false`() {
        viewModel = createViewModel(TEST_KOBIS_MOVIES, TEST_POSTER_URLS)

        coVerify { kobisRepository.getMovies(any()) }
        coVerify { tmdbRepository.getPosterUrl(any()) }
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `when movies are loading then isLoading should be true and false after completion`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        viewModel = createViewModel(TEST_KOBIS_MOVIES, TEST_POSTER_URLS)

        assertEquals(true, viewModel.isLoading.value)
        advanceUntilIdle()
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `when onTabSelected WEEKLY then selectedTab should be WEEKLY and repository called`() {
        viewModel = createViewModel(TEST_KOBIS_MOVIES, TEST_POSTER_URLS)
        viewModel.onTabSelected(TabType.WEEKLY)

        assertEquals(TabType.WEEKLY, viewModel.selectedTab.value)
        coVerify { kobisRepository.getMovies(TabType.WEEKLY) }
    }

    @Test
    fun `when kobisMovie and posterUrl are combined then Movie should be returned correctly`() {
        val testKobisMovies = listOf(
            KobisMovie("1", "20243561", "어쩔수가없다", "45.3", "833401"),
            KobisMovie("2", "20256757", "극장판 체인소 맨: 레제편", "24.2", "368903")
        )
        val testPosterUrls = mapOf(
            "639988" to "test1.jpg",
            "1218925" to "test2.jpg"
        )
        stubKobisRepository(testKobisMovies)
        stubTmdbRepository(testPosterUrls)
        val viewModel = MainViewModel(kobisRepository, tmdbRepository, movieIdMapper)

        verify(exactly = 2) { movieIdMapper.getTmdbId(any()) }
        coVerify(exactly = 2) { tmdbRepository.getPosterUrl(any()) }
        val expectedMovies = listOf(
            Movie("1", "20243561", "어쩔수가없다", "45.3", "833401", "test1.jpg"),
            Movie("2", "20256757", "극장판 체인소 맨: 레제편", "24.2", "368903", "test2.jpg")
        )
        assertEquals(expectedMovies, viewModel.movies.value)
    }

    @Test
    fun `when getTmdbId called with kobis id then correct tmdb id should be returned`() {
        assertEquals("639988", movieIdMapper.getTmdbId("20243561"))
        assertEquals("1218925", movieIdMapper.getTmdbId("20256757"))
        verify(exactly = 2) { movieIdMapper.getTmdbId(any()) }
    }

    @Test
    fun `when repository returns empty list then movies should be empty`() {
        viewModel = createViewModel(emptyList(), emptyMap())

        assertEquals(emptyList<Movie>(), viewModel.movies.value)
        coVerify(exactly = 1) { kobisRepository.getMovies(any()) }
        coVerify(exactly = 0) { tmdbRepository.getPosterUrl(any()) }
    }

    @Test
    fun `when posterUrl is not available then movie should have empty posterUrl`() {
        val testKobisMovie = listOf(KobisMovie("1", "20243561", "어쩔수가없다", "45.3", "833401"))
        stubKobisRepository(testKobisMovie)
        coEvery { tmdbRepository.getPosterUrl(any()) } returns ""
        viewModel = MainViewModel(kobisRepository, tmdbRepository, movieIdMapper)

        val expectedMovie = listOf(Movie("1", "20243561", "어쩔수가없다", "45.3", "833401", ""))
        assertEquals(expectedMovie, viewModel.movies.value)
    }

    @Test
    fun `when kobis id is not mapped then tmdb id should be empty`() {
        val unmappedKobisMovie = listOf(KobisMovie("1", "0", "unknown", "10.0", "10"))
        stubKobisRepository(unmappedKobisMovie)
        coEvery { tmdbRepository.getPosterUrl("") } returns ""
        viewModel = MainViewModel(kobisRepository, tmdbRepository, movieIdMapper)

        assertEquals("", movieIdMapper.getTmdbId("0"))
        val expectedMovie = listOf(Movie("1", "0", "unknown", "10.0", "10", ""))
        assertEquals(expectedMovie, viewModel.movies.value)
    }

    @Test
    fun `when switching between tabs then movies should update correctly`() {
        val dailyMovies = listOf(KobisMovie("1", "20243561", "어쩔수가없다", "45.3", "833401"))
        val dailyPosterUrls = mapOf("639988" to "daily.jpg")
        val weeklyMovies = listOf(KobisMovie("1", "20242964", "얼굴", "30.0", "500000"))
        val weeklyPosterUrls = mapOf("1316719" to "weekly.jpg")

        coEvery { kobisRepository.getMovies(TabType.DAILY) } returns dailyMovies
        stubTmdbRepository(dailyPosterUrls)
        viewModel = MainViewModel(kobisRepository, tmdbRepository, movieIdMapper)

        val expectedDailyMovie = listOf(Movie("1", "20243561", "어쩔수가없다", "45.3", "833401", "daily.jpg"))
        assertEquals(expectedDailyMovie, viewModel.movies.value)

        coEvery { kobisRepository.getMovies(TabType.WEEKLY) } returns weeklyMovies
        stubTmdbRepository(weeklyPosterUrls)
        viewModel.onTabSelected(TabType.WEEKLY)
        val expectedWeeklyMovie = listOf(Movie("1", "20242964", "얼굴", "30.0", "500000", "weekly.jpg"))
        assertEquals(expectedWeeklyMovie, viewModel.movies.value)

        viewModel.onTabSelected(TabType.DAILY)
        assertEquals(expectedDailyMovie, viewModel.movies.value)
    }

    @Test
    fun `when same tab is selected again then repository should not be called`() {
        viewModel = createViewModel(TEST_KOBIS_MOVIES, TEST_POSTER_URLS)

        viewModel.onTabSelected(TabType.DAILY)

        coVerify(exactly = 1) { kobisRepository.getMovies(TabType.DAILY) }
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
        private val TEST_POSTER_URLS = mapOf(
            "639988" to "https://image.tmdb.org/t/p/w500/test1.jpg",
            "1218925" to "https://image.tmdb.org/t/p/w500/test2.jpg"
        )
    }

}
