package com.chaeny.moviechart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor() : ViewModel() {

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
        _isLoading.value = true
        viewModelScope.launch {
            delay(3000)
            _movies.value = DummyMovieData.movies
            _isLoading.value = false
        }
    }
}
