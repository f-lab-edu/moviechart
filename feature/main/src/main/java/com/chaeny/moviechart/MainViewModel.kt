package com.chaeny.moviechart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeny.moviechart.repository.GetMoviesResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val getMoviesWithPostersUseCase: GetMoviesWithPostersUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    private val _selectedType = MutableStateFlow(PeriodType.DAILY)
    private val _isLoading = MutableStateFlow(false)

    val movies: StateFlow<List<Movie>> = _movies
    val selectedType: StateFlow<PeriodType> = _selectedType
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadMovies()
    }

    fun onTypeSelected(periodType: PeriodType) {
        if (_selectedType.value == periodType) return
        _selectedType.value = periodType
        loadMovies()
    }

    private fun loadMovies() {
        _movies.value = emptyList()
        _isLoading.value = true
        viewModelScope.launch {
            val result = getMoviesWithPostersUseCase(_selectedType.value)
            handleMoviesResult(result)
            _isLoading.value = false
        }
    }

    private fun handleMoviesResult(result: GetMoviesResult) {
        when (result) {
            is GetMoviesResult.Success -> {
                val moviesWithPosters = result.movies
                _movies.value = moviesWithPosters
            }
            is GetMoviesResult.NoResult -> {
                Log.w("MainViewModel", "NoResult")
                _movies.value = emptyList()
            }
            is GetMoviesResult.NoInternet -> {
                Log.e("MainViewModel", "NoInternet")
                _movies.value = emptyList()
            }
            is GetMoviesResult.NetworkError -> {
                Log.e("MainViewModel", "NetworkError")
                _movies.value = emptyList()
            }
        }
    }
}
