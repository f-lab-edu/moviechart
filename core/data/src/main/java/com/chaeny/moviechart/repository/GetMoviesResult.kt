package com.chaeny.moviechart.repository

import com.chaeny.moviechart.Movie

sealed class GetMoviesResult {
    data class Success(val movies: List<Movie>) : GetMoviesResult()
    data object NoResult : GetMoviesResult()
    data object NoInternet : GetMoviesResult()
    data object NetworkError : GetMoviesResult()
}
