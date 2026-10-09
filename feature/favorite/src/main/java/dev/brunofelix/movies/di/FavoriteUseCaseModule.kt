package dev.brunofelix.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.domain.use_case.GetFavoriteMediasUseCase
import dev.brunofelix.movies.domain.use_case.GetFavoriteMediasUseCaseImpl
import dev.brunofelix.movies.domain.use_case.SyncFavoriteWatchProvidersUseCase
import dev.brunofelix.movies.domain.use_case.SyncFavoriteWatchProvidersUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteUseCaseModule {

    @Binds
    abstract fun bindGetFavoriteMediasUseCase(
        impl: GetFavoriteMediasUseCaseImpl
    ): GetFavoriteMediasUseCase

    @Binds
    abstract fun bindSyncFavoriteWatchProvidersUseCase(
        impl: SyncFavoriteWatchProvidersUseCaseImpl
    ): SyncFavoriteWatchProvidersUseCase
}
