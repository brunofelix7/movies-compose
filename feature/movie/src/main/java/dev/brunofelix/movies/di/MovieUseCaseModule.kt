package dev.brunofelix.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.domain.use_case.GetMovieCastUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieCastUseCaseImpl
import dev.brunofelix.movies.domain.use_case.GetMovieDetailUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieDetailUseCaseImpl
import dev.brunofelix.movies.domain.use_case.GetMovieVideosUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieVideosUseCaseImpl
import dev.brunofelix.movies.domain.use_case.GetPopularMoviesUseCase
import dev.brunofelix.movies.domain.use_case.GetPopularMoviesUseCaseImpl
import dev.brunofelix.movies.domain.use_case.GetTopRatedMoviesUseCase
import dev.brunofelix.movies.domain.use_case.GetTopRatedMoviesUseCaseImpl
import dev.brunofelix.movies.domain.use_case.GetUpcomingMoviesUseCase
import dev.brunofelix.movies.domain.use_case.GetUpcomingMoviesUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieUseCaseModule {

    @Binds
    abstract fun bindGetMovieCastUseCase(
        impl: GetMovieCastUseCaseImpl
    ): GetMovieCastUseCase

    @Binds
    abstract fun bindGetMovieDetailUseCase(
        impl: GetMovieDetailUseCaseImpl
    ): GetMovieDetailUseCase

    @Binds
    abstract fun bindGetMovieVideosUseCase(
        impl: GetMovieVideosUseCaseImpl
    ): GetMovieVideosUseCase

    @Binds
    abstract fun bindGetPopularMoviesUseCase(
        impl: GetPopularMoviesUseCaseImpl
    ): GetPopularMoviesUseCase

    @Binds
    abstract fun bindGetTopRatedMoviesUseCase(
        impl: GetTopRatedMoviesUseCaseImpl
    ): GetTopRatedMoviesUseCase

    @Binds
    abstract fun bindGetUpcomingMoviesUseCase(
        impl: GetUpcomingMoviesUseCaseImpl
    ): GetUpcomingMoviesUseCase
}
