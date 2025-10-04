package com.chaeny.moviechart.di

import com.chaeny.moviechart.repository.DummyMovieRepository
import com.chaeny.moviechart.repository.MovieRepository
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
    abstract fun bindMovieRepository(dummyMovieRepository: DummyMovieRepository): MovieRepository
}
