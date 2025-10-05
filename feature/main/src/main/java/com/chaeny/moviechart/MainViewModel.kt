package com.chaeny.moviechart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeny.moviechart.mapper.MovieIdMapper
import com.chaeny.moviechart.repository.KobisRepository
import com.chaeny.moviechart.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val kobisRepository: KobisRepository,
    private val tmdbRepository: TmdbRepository,
    private val movieIdMapper: MovieIdMapper
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    private val _selectedTab = MutableStateFlow(TabType.DAILY)
    private val _isLoading = MutableStateFlow(false)

    val movies: StateFlow<List<Movie>> = _movies
    val selectedTab: StateFlow<TabType> = _selectedTab
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadMovies()
    }

    fun onTabSelected(tabType: TabType) {
        _selectedTab.value = tabType
        loadMovies()
    }

    private fun loadMovies() {
        _movies.value = emptyList()
        _isLoading.value = true
        viewModelScope.launch {
            _movies.value = getUpdatedMovies(kobisRepository.getMovies())
            _isLoading.value = false
        }
    }

    private suspend fun getUpdatedMovies(movies: List<Movie>) : List<Movie> {
        return coroutineScope {
            movies.map { movie ->
                async {
                    movie.copy(posterUrl = tmdbRepository.getPosterUrl(movieIdMapper.getTmdbId(movie.movieId)!!))
                }
            }.awaitAll()
        }
    }
}
