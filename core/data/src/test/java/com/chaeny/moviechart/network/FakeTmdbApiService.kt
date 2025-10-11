package com.chaeny.moviechart.network

import com.chaeny.moviechart.model.TmdbMovieResponse

class FakeTmdbApiService : TmdbApiService {

    var response: TmdbMovieResponse = TmdbMovieResponse(poster_path = "")
    var throwException = false
    var exception: Exception = Exception()

    override suspend fun getMovieDetails(
        movieId: String,
        apiKey: String,
        language: String
    ): TmdbMovieResponse {
        if (throwException) {
            throw exception
        }
        return response
    }
}
