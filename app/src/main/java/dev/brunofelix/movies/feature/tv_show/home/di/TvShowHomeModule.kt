package dev.brunofelix.movies.feature.tv_show.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetPopularTvShowsUseCase
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetPopularTvShowsUseCaseImpl
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetTopRatedTvShowsUseCase
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetTopRatedTvShowsUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class TvShowHomeModule {

    @Binds
    abstract fun bindGetPopularTvShowsUseCase(
        impl: GetPopularTvShowsUseCaseImpl
    ): GetPopularTvShowsUseCase

    @Binds
    abstract fun bindGetTopRatedTvShowsUseCase(
        impl: GetTopRatedTvShowsUseCaseImpl
    ): GetTopRatedTvShowsUseCase
}
