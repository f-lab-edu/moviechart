package com.chaeny.moviechart.usecase

import com.chaeny.moviechart.PeriodType
import com.chaeny.moviechart.Movie
import com.chaeny.moviechart.mapper.MovieIdMapper
import com.chaeny.moviechart.repository.GetMoviesResult
import com.chaeny.moviechart.repository.KobisRepository
import com.chaeny.moviechart.repository.TmdbRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DefaultGetMoviesWithPostersUseCase @Inject constructor(
    private val kobisRepository: KobisRepository,
    private val tmdbRepository: TmdbRepository,
    private val movieIdMapper: MovieIdMapper
) : GetMoviesWithPostersUseCase {
    override suspend operator fun invoke(periodType: PeriodType): GetMoviesResult {
        val result = kobisRepository.getMovies(periodType)
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
