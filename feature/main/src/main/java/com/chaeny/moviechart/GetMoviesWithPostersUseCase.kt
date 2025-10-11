package com.chaeny.moviechart

import com.chaeny.moviechart.repository.GetMoviesResult

interface GetMoviesWithPostersUseCase {
    suspend operator fun invoke(periodType: PeriodType): GetMoviesResult
}
