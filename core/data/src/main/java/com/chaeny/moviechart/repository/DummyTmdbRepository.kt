package com.chaeny.moviechart.repository

import kotlinx.coroutines.delay
import javax.inject.Inject

class DummyTmdbRepository @Inject constructor() : TmdbRepository {

    private val dummyPosterData: Map<String, String> = mapOf(
        "639988" to "https://image.tmdb.org/t/p/w500/pf7vZxoLYtLQ366VNlGrjBxwL7A.jpg",
        "1218925" to "https://image.tmdb.org/t/p/w500/Amu0HNWfpxo2ZaulueNVxDLADz8.jpg",
        "1311031" to "https://image.tmdb.org/t/p/w500/m6Dho6hDCcL5KI8mOQNemZAedFI.jpg",
        "1316719" to "https://image.tmdb.org/t/p/w500/9RsHtbUMXMfHjkL74BhM7KFEozT.jpg",
        "1521915" to "https://image.tmdb.org/t/p/w500/cwvehMf8bnWwUhKOFR6qHTxg1VO.jpg",
        "128" to "https://image.tmdb.org/t/p/w500/gEVSN7rzQsypG4YfYObsPmMtYpP.jpg",
        "911430" to "https://image.tmdb.org/t/p/w500/bvVoP1t2gNvmE9ccSrqR1zcGHGM.jpg",
        "1228018" to "https://image.tmdb.org/t/p/w500/eSNprN73xK9LKN8f5y5Ee446QzK.jpg",
        "1540229" to "https://image.tmdb.org/t/p/w500/kI9ffyOEwj0bdpttdPEtAVDFHxC.jpg",
        "1519318" to "https://image.tmdb.org/t/p/w500/rXyniH1Xyp3xksHzZ0wSU6IqDjh.jpg"
    )

    override suspend fun getPosterUrl(movieId: String): String {
        delay(1000)
        return dummyPosterData[movieId] ?: ""
    }
}
