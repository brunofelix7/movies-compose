package dev.brunofelix.movies.feature.popular.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.feature.popular.domain.use_case.GetPopularUseCase
import dev.brunofelix.movies.feature.popular.domain.use_case.GetPopularUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PopularModule {

    @Provides
    @Singleton
    fun provideGetPopularUseCase(
        repository: MovieRepository
    ): GetPopularUseCase {
        return GetPopularUseCaseImpl(repository)
    }
}