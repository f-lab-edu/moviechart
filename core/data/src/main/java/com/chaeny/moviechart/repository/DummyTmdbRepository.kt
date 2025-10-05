package com.chaeny.moviechart.repository

import kotlinx.coroutines.delay
import javax.inject.Inject

class DummyTmdbRepository @Inject constructor() : TmdbRepository {

    private val dummyPosterPaths: Map<String, String> = mapOf(
        "639988" to "/pf7vZxoLYtLQ366VNlGrjBxwL7A.jpg",
        "1218925" to "/Amu0HNWfpxo2ZaulueNVxDLADz8.jpg",
        "1311031" to "/m6Dho6hDCcL5KI8mOQNemZAedFI.jpg",
        "1316719" to "/9RsHtbUMXMfHjkL74BhM7KFEozT.jpg",
        "1521915" to "/cwvehMf8bnWwUhKOFR6qHTxg1VO.jpg",
        "128" to "/gEVSN7rzQsypG4YfYObsPmMtYpP.jpg",
        "911430" to "/bvVoP1t2gNvmE9ccSrqR1zcGHGM.jpg",
        "1228018" to "/eSNprN73xK9LKN8f5y5Ee446QzK.jpg",
        "1540229" to "/kI9ffyOEwj0bdpttdPEtAVDFHxC.jpg",
        "1519318" to "/rXyniH1Xyp3xksHzZ0wSU6IqDjh.jpg",
        "1151766" to "/rcmJRflS4aFJg8Ockk7YePQWlzc.jpg"
    )

    override suspend fun getPosterUrl(movieId: String): String {
        delay(1000)
        val posterPath = dummyPosterPaths[movieId] ?: ""
        return "$TMDB_IMAGE_BASE_URL$posterPath"
    }

    companion object {
        private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
