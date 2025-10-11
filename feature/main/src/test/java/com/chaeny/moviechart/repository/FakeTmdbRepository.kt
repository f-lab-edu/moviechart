package com.chaeny.moviechart.repository

class FakeTmdbRepository : TmdbRepository {

    lateinit var posterUrls: Map<String, String>

    override suspend fun getPosterUrl(tmdbId: String): String {
        return posterUrls[tmdbId] ?: ""
    }
}
