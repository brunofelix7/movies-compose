package dev.brunofelix.movies.feature.upcoming.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.feature.upcoming.domain.use_case.GetUpcomingUseCase
import dev.brunofelix.movies.feature.upcoming.domain.use_case.GetUpcomingUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UpcomingModule {

    @Provides
    @Singleton
    fun provideGetUpcomingUseCase(
        repository: MovieRepository
    ): GetUpcomingUseCase {
        return GetUpcomingUseCaseImpl(repository)
    }
}