package dev.brunofelix.movies.feature.detail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.detail.domain.use_case.DeleteMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.DeleteMovieUseCaseImpl
import dev.brunofelix.movies.feature.detail.domain.use_case.GetMovieDetailsUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.GetMovieDetailsUseCaseImpl
import dev.brunofelix.movies.feature.detail.domain.use_case.IsFavoriteMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.IsFavoriteMovieUseCaseImpl
import dev.brunofelix.movies.feature.detail.domain.use_case.SaveMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.SaveMovieUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDetailModule {

    @Binds
    abstract fun bindGetMovieDetailsUseCase(
        impl: GetMovieDetailsUseCaseImpl
    ): GetMovieDetailsUseCase

    @Binds
    abstract fun bindSaveMovieUseCase(
        impl: SaveMovieUseCaseImpl
    ): SaveMovieUseCase

    @Binds
    abstract fun bindDeleteMovieUseCase(
        impl: DeleteMovieUseCaseImpl
    ): DeleteMovieUseCase

    @Binds
    abstract fun bindIsFavoriteMovieUseCase(
        impl: IsFavoriteMovieUseCaseImpl
    ): IsFavoriteMovieUseCase
}