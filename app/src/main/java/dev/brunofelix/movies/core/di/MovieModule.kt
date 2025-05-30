package dev.brunofelix.movies.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.data.repository.MovieRepositoryImpl
import dev.brunofelix.movies.core.domain.data_source.MovieLocalDataSource
import dev.brunofelix.movies.core.domain.data_source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.feature.details.domain.use_case.DeleteMovieUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.DeleteMovieUseCaseImpl
import dev.brunofelix.movies.feature.details.domain.use_case.GetDetailsUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.GetDetailsUseCaseImpl
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoritesUseCase
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoritesUseCaseImpl
import dev.brunofelix.movies.feature.popular.domain.use_case.GetPopularsUseCase
import dev.brunofelix.movies.feature.popular.domain.use_case.GetPopularsUseCaseImpl
import dev.brunofelix.movies.feature.upcoming.domain.use_case.GetUpcomingUseCase
import dev.brunofelix.movies.feature.upcoming.domain.use_case.GetUpcomingUseCaseImpl
import dev.brunofelix.movies.feature.details.domain.use_case.IsFavoriteUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.IsFavoriteUseCaseImpl
import dev.brunofelix.movies.feature.details.domain.use_case.SaveMovieUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.SaveMovieUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

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
    fun provideGetPopularsUseCase(
        repository: MovieRepository
    ): GetPopularsUseCase {
        return GetPopularsUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideGetUpcomingUseCase(
        repository: MovieRepository
    ): GetUpcomingUseCase {
        return GetUpcomingUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideGetDetailsUseCase(
        repository: MovieRepository
    ): GetDetailsUseCase {
        return GetDetailsUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideSaveMovieUseCase(
        repository: MovieRepository
    ): SaveMovieUseCase {
        return SaveMovieUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteMovieUseCase(
        repository: MovieRepository
    ): DeleteMovieUseCase {
        return DeleteMovieUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideIsFavoriteUseCase(
        repository: MovieRepository
    ): IsFavoriteUseCase {
        return IsFavoriteUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavoritesUseCase(
        repository: MovieRepository
    ): GetFavoritesUseCase {
        return GetFavoritesUseCaseImpl(repository)
    }
}