package com.chaeny.moviechart.usecase

import com.chaeny.moviechart.PeriodType
import com.chaeny.moviechart.repository.GetMoviesResult

class FakeGetMoviesWithPostersUseCase : GetMoviesWithPostersUseCase {

    var result: GetMoviesResult = GetMoviesResult.Success(emptyList())

    override suspend operator fun invoke(periodType: PeriodType): GetMoviesResult {
        return result
    }
}
