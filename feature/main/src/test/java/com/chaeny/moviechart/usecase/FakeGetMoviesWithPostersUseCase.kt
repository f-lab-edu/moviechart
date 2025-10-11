package com.chaeny.moviechart.usecase

import com.chaeny.moviechart.PeriodType
import com.chaeny.moviechart.repository.GetMoviesResult

class FakeGetMoviesWithPostersUseCase : GetMoviesWithPostersUseCase {

    var result: GetMoviesResult = GetMoviesResult.Success(emptyList())
    var resultByType: Map<PeriodType, GetMoviesResult> = emptyMap()
    var callCount = 0

    override suspend operator fun invoke(periodType: PeriodType): GetMoviesResult {
        callCount++
        return resultByType[periodType] ?: result
    }
}
