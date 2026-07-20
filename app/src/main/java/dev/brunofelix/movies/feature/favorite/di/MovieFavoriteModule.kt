package dev.brunofelix.movies.feature.favorite.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMoviesUseCase
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMoviesUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieFavoriteModule {

    @Binds
    abstract fun bindGetFavoriteMoviesUseCase(
        impl: GetFavoriteMoviesUseCaseImpl
    ): GetFavoriteMoviesUseCase
}