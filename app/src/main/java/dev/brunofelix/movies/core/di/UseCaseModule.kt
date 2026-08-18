package dev.brunofelix.movies.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.DeleteMediaUseCaseImpl
import dev.brunofelix.movies.core.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.IsFavoriteMediaUseCaseImpl
import dev.brunofelix.movies.core.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.SaveMediaUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindSaveMovieUseCase(
        impl: SaveMediaUseCaseImpl
    ): SaveMediaUseCase

    @Binds
    abstract fun bindDeleteMediaUseCase(
        impl: DeleteMediaUseCaseImpl
    ): DeleteMediaUseCase

    @Binds
    abstract fun bindIsFavoriteMovieUseCase(
        impl: IsFavoriteMediaUseCaseImpl
    ): IsFavoriteMediaUseCase
}