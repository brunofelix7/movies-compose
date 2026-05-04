package dev.brunofelix.movies.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.data.repository.MovieRepositoryImpl
import dev.brunofelix.movies.core.data.source.MovieLocalDataSource
import dev.brunofelix.movies.core.data.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.feature.detail.domain.use_case.DeleteMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.DeleteMovieUseCaseImpl
import dev.brunofelix.movies.feature.detail.domain.use_case.GetMovieDetailsUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.GetMovieDetailsUseCaseImpl
import dev.brunofelix.movies.feature.detail.domain.use_case.IsFavoriteMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.IsFavoriteMovieUseCaseImpl
import dev.brunofelix.movies.feature.detail.domain.use_case.SaveMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.SaveMovieUseCaseImpl
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMoviesUseCase
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMoviesUseCaseImpl
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
    fun provideGetMovieDetailsUseCase(
        repository: MovieRepository
    ): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCaseImpl(repository)
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
    fun provideIsFavoriteMovieUseCase(
        repository: MovieRepository
    ): IsFavoriteMovieUseCase {
        return IsFavoriteMovieUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteMoviesUseCase(
        repository: MovieRepository
    ): GetFavoriteMoviesUseCase {
        return GetFavoriteMoviesUseCaseImpl(repository)
    }
}