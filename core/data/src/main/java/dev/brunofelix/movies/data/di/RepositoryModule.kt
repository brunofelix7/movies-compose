package dev.brunofelix.movies.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.data.repository.AppInfoRepositoryImpl
import dev.brunofelix.movies.data.repository.LanguageRepositoryImpl
import dev.brunofelix.movies.data.repository.MediaRepositoryImpl
import dev.brunofelix.movies.data.repository.MovieRepositoryImpl
import dev.brunofelix.movies.data.repository.SearchRepositoryImpl
import dev.brunofelix.movies.data.repository.TvShowRepositoryImpl
import dev.brunofelix.movies.domain.repository.AppInfoRepository
import dev.brunofelix.movies.domain.repository.LanguageRepository
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.repository.SearchRepository
import dev.brunofelix.movies.domain.repository.TvShowRepository
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

    @Binds
    @Singleton
    abstract fun bindMediaRepository(
        impl: MediaRepositoryImpl
    ): MediaRepository

    @Binds
    @Singleton
    abstract fun bindLanguageRepository(
        impl: LanguageRepositoryImpl
    ): LanguageRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindAppInfoRepository(
        impl: AppInfoRepositoryImpl
    ): AppInfoRepository
}
