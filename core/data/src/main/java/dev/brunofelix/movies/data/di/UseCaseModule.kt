package dev.brunofelix.movies.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCaseImpl
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCaseImpl
import dev.brunofelix.movies.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.domain.use_case.IsFavoriteMediaUseCaseImpl
import dev.brunofelix.movies.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.domain.use_case.SaveMediaUseCaseImpl
import dev.brunofelix.movies.domain.use_case.UpdateFavoriteWatchProvidersUseCase
import dev.brunofelix.movies.domain.use_case.UpdateFavoriteWatchProvidersUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindDeleteMediaUseCase(
        impl: DeleteMediaUseCaseImpl
    ): DeleteMediaUseCase

    @Binds
    abstract fun bindGetLanguageUseCase(
        impl: GetLanguageUseCaseImpl
    ): GetLanguageUseCase

    @Binds
    abstract fun bindIsFavoriteMediaUseCase(
        impl: IsFavoriteMediaUseCaseImpl
    ): IsFavoriteMediaUseCase

    @Binds
    abstract fun bindSaveMediaUseCase(
        impl: SaveMediaUseCaseImpl
    ): SaveMediaUseCase

    @Binds
    abstract fun bindUpdateFavoriteWatchProvidersUseCase(
        impl: UpdateFavoriteWatchProvidersUseCaseImpl
    ): UpdateFavoriteWatchProvidersUseCase
}
