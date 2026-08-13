package dev.brunofelix.movies.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.data.repository.MovieRepositoryImpl
import dev.brunofelix.movies.core.data.repository.TvShowRepositoryImpl
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindTvShowRepository(
        impl: TvShowRepositoryImpl
    ): TvShowRepository
}