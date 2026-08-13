package dev.brunofelix.movies.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.data.repository.MovieRepositoryImpl
import dev.brunofelix.movies.core.data.repository.TvShowRepositoryImpl
import dev.brunofelix.movies.core.data.local.source.MovieLocalDataSource
import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource,
        localDataSource: MovieLocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(
        remoteDataSource: TvShowRemoteDataSource
    ): TvShowRepository {
        return TvShowRepositoryImpl(remoteDataSource)
    }
}