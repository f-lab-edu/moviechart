package com.chaeny.moviechart.network

import com.chaeny.moviechart.BuildConfig
import com.chaeny.moviechart.model.TmdbMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "ko"
    ): TmdbMovieResponse
}
