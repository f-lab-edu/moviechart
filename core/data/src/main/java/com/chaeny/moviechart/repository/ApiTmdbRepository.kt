package com.chaeny.moviechart.repository

import android.util.Log
import com.chaeny.moviechart.network.TmdbApiService
import java.io.IOException
import javax.inject.Inject

class ApiTmdbRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) : TmdbRepository {

    override suspend fun getPosterUrl(movieId: String): String {
        return try {
            val response = tmdbApiService.getMovieDetails(movieId = movieId)
            response.poster_path.toPosterUrl()
        } catch (e: IOException) {
            Log.e("ApiTmdbRepository", "getPosterUrl(movieId=$movieId) - IOException: ${e.message}")
            ""
        } catch (e: Exception) {
            Log.e("ApiTmdbRepository", "getPosterUrl(movieId=$movieId) - Exception: ${e.message}")
            ""
        }
    }

    private fun String.toPosterUrl(): String {
        if (isEmpty()) return ""
        return "$TMDB_IMAGE_BASE_URL$this"
    }

    companion object {
        private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
