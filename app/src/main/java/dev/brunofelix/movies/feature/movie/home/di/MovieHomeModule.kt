package dev.brunofelix.movies.feature.movie.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetPopularUseCase
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetPopularUseCaseImpl
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetTopRatedUseCase
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetTopRatedUseCaseImpl
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetUpcomingUseCase
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetUpcomingUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieHomeModule {

    @Binds
    abstract fun bindGetPopularUseCase(
        impl: GetPopularUseCaseImpl
    ): GetPopularUseCase

    @Binds
    abstract fun bindGetUpcomingUseCase(
        impl: GetUpcomingUseCaseImpl
    ): GetUpcomingUseCase

    @Binds
    abstract fun bindGetTopRatedUseCase(
        impl: GetTopRatedUseCaseImpl
    ): GetTopRatedUseCase
}
