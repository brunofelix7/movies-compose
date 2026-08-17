package dev.brunofelix.movies.feature.movie.detail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieDetailUseCase
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieDetailUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDetailModule {

    @Binds
    abstract fun bindGetMovieDetailsUseCase(
        impl: GetMovieDetailUseCaseImpl
    ): GetMovieDetailUseCase
}