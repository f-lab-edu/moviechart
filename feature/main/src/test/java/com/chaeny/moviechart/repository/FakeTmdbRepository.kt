package com.chaeny.moviechart.repository

class FakeTmdbRepository : TmdbRepository {

    override suspend fun getPosterUrl(tmdbId: String): String {
        return when (tmdbId) {
            "639988" -> "https://image.tmdb.org/t/p/w500/test1.jpg"
            "1218925" -> "https://image.tmdb.org/t/p/w500/test2.jpg"
            else -> ""
        }
    }
}
