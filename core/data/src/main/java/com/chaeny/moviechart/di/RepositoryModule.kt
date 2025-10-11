package com.chaeny.moviechart.di

import com.chaeny.moviechart.repository.ApiKobisRepository
import com.chaeny.moviechart.repository.ApiTmdbRepository
import com.chaeny.moviechart.repository.KobisRepository
import com.chaeny.moviechart.repository.TmdbRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindKobisRepository(implementation: ApiKobisRepository): KobisRepository

    @Binds
    @Singleton
    abstract fun bindTmdbRepository(implementation: ApiTmdbRepository): TmdbRepository
}
