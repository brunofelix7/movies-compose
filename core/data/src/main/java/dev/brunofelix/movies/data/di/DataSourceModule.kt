package dev.brunofelix.movies.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.data.local.preferences.PreferenceStorage
import dev.brunofelix.movies.data.local.preferences.PreferenceStorageImpl
import dev.brunofelix.movies.data.local.source.AppInfoLocalDataSource
import dev.brunofelix.movies.data.local.source.AppInfoLocalDataSourceImpl
import dev.brunofelix.movies.data.local.source.LanguageLocalDataSource
import dev.brunofelix.movies.data.local.source.LanguageLocalDataSourceImpl
import dev.brunofelix.movies.data.local.source.MediaLocalDataSource
import dev.brunofelix.movies.data.local.source.MediaLocalDataSourceImpl
import dev.brunofelix.movies.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.data.remote.source.MovieRemoteDataSourceImpl
import dev.brunofelix.movies.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.data.remote.source.TvShowRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindPreferenceStorage(
        impl: PreferenceStorageImpl
    ): PreferenceStorage

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(
        impl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTvShowRemoteDataSource(
        impl: TvShowRemoteDataSourceImpl
    ): TvShowRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMediaLocalDataSource(
        impl: MediaLocalDataSourceImpl
    ): MediaLocalDataSource

    @Binds
    @Singleton
    abstract fun bindLanguageLocalDataSource(
        impl: LanguageLocalDataSourceImpl
    ): LanguageLocalDataSource

    @Binds
    @Singleton
    abstract fun bindAppInfoLocalDataSource(
        impl: AppInfoLocalDataSourceImpl
    ): AppInfoLocalDataSource
}
