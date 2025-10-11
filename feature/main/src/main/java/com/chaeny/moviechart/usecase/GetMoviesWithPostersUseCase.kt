package com.chaeny.moviechart.usecase

import com.chaeny.moviechart.PeriodType
import com.chaeny.moviechart.repository.GetMoviesResult

interface GetMoviesWithPostersUseCase {
    suspend operator fun invoke(periodType: PeriodType): GetMoviesResult
}
