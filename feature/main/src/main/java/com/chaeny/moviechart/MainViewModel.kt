package com.chaeny.moviechart

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor() : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    private val _selectedTab = MutableStateFlow(TabType.DAILY)
    val movies: StateFlow<List<Movie>> = _movies
    val selectedTab: StateFlow<TabType> = _selectedTab

    init {
        loadMovies()
    }

    fun onTabSelected(tabType: TabType) {
        _selectedTab.value = tabType
        loadMovies()
    }

    private fun loadMovies() {
        _movies.value = DummyMovieData.movies
    }
}
