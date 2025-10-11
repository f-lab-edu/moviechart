package com.chaeny.moviechart.di

import com.chaeny.moviechart.DefaultGetMoviesWithPostersUseCase
import com.chaeny.moviechart.GetMoviesWithPostersUseCase
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
