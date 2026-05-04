package dev.brunofelix.movies.feature.favorite.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMoviesUseCase
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMoviesUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    @Provides
    @Singleton
    fun provideGetFavoriteMoviesUseCase(
        repository: MovieRepository
    ): GetFavoriteMoviesUseCase {
        return GetFavoriteMoviesUseCaseImpl(repository)
    }
}