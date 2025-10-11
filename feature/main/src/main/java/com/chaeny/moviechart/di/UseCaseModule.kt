package com.chaeny.moviechart.di

import com.chaeny.moviechart.usecase.DefaultGetMoviesWithPostersUseCase
import com.chaeny.moviechart.usecase.GetMoviesWithPostersUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetMoviesWithPostersUseCase(
        implementation: DefaultGetMoviesWithPostersUseCase
    ): GetMoviesWithPostersUseCase
}
