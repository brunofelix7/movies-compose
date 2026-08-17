package dev.brunofelix.movies.feature.favorite.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMediasUseCase
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMediasUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteModule {

    @Binds
    abstract fun bindGetFavoriteMediasUseCase(
        impl: GetFavoriteMediasUseCaseImpl
    ): GetFavoriteMediasUseCase
}