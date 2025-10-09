package com.chaeny.moviechart.repository

import com.chaeny.moviechart.network.TmdbApiService
import javax.inject.Inject

class ApiTmdbRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) : TmdbRepository {

    override suspend fun getPosterUrl(movieId: String): String {
        val response = tmdbApiService.getMovieDetails(movieId = movieId)
        return response.poster_path.toPosterUrl()
    }

    private fun String.toPosterUrl(): String {
        if (isEmpty()) return ""
        return "$TMDB_IMAGE_BASE_URL$this"
    }

    companion object {
        private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
