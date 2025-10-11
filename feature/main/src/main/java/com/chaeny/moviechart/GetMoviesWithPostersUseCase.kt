package com.chaeny.moviechart

import com.chaeny.moviechart.mapper.MovieIdMapper
import com.chaeny.moviechart.repository.GetMoviesResult
import com.chaeny.moviechart.repository.KobisRepository
import com.chaeny.moviechart.repository.TmdbRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetMoviesWithPostersUseCase @Inject constructor(
    private val kobisRepository: KobisRepository,
    private val tmdbRepository: TmdbRepository,
    private val movieIdMapper: MovieIdMapper
) {
    suspend operator fun invoke(tabType: TabType): GetMoviesResult {
        val result = kobisRepository.getMovies(tabType)
        return when (result) {
            is GetMoviesResult.Success -> {
                val moviesWithPosters = loadMoviePosters(result.movies)
                GetMoviesResult.Success(moviesWithPosters)
            }
            else -> result
        }
    }

    private suspend fun loadMoviePosters(movies: List<Movie>): List<Movie> {
        return coroutineScope {
            movies.map { movie ->
                async {
                    val tmdbId = movieIdMapper.getTmdbId(movie.id)
                    val posterUrl = tmdbRepository.getPosterUrl(tmdbId)
                    movie.copy(posterUrl = posterUrl)
                }
            }.awaitAll()
        }
    }
}
