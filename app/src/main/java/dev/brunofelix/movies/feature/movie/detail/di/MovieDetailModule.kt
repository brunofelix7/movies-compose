package dev.brunofelix.movies.feature.movie.detail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieCastUseCase
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieCastUseCaseImpl
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieDetailUseCase
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieDetailUseCaseImpl
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieVideosUseCase
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieVideosUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDetailModule {

    @Binds
    abstract fun bindGetMovieDetailsUseCase(
        impl: GetMovieDetailUseCaseImpl
    ): GetMovieDetailUseCase

    @Binds
    abstract fun bindGetMovieVideosUseCase(
        impl: GetMovieVideosUseCaseImpl
    ): GetMovieVideosUseCase

    @Binds
    abstract fun bindGetMovieCastUseCase(
        impl: GetMovieCastUseCaseImpl
    ): GetMovieCastUseCase
}