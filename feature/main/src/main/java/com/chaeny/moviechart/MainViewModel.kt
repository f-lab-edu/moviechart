package com.chaeny.moviechart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeny.moviechart.repository.GetMoviesResult
import com.chaeny.moviechart.usecase.GetMoviesWithPostersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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
    private val _loadEvent = MutableSharedFlow<LoadEvent>()

    val movies: StateFlow<List<Movie>> = _movies
    val selectedType: StateFlow<PeriodType> = _selectedType
    val isLoading: StateFlow<Boolean> = _isLoading
    val loadEvent: SharedFlow<LoadEvent> = _loadEvent

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

    private suspend fun handleMoviesResult(result: GetMoviesResult) {
        when (result) {
            is GetMoviesResult.Success -> {
                val moviesWithPosters = result.movies
                _movies.value = moviesWithPosters
            }
            GetMoviesResult.NoResult -> {
                _movies.value = emptyList()
                _loadEvent.emit(LoadEvent.NoResult)
            }
            GetMoviesResult.NoInternet -> {
                _movies.value = emptyList()
                _loadEvent.emit(LoadEvent.NoInternet)
            }
            GetMoviesResult.NetworkError -> {
                _movies.value = emptyList()
                _loadEvent.emit(LoadEvent.NetworkError)
            }
        }
    }
}
