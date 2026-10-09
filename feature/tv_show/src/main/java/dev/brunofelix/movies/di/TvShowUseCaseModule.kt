package dev.brunofelix.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.data.use_case.GetPopularTvShowsUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetSeasonEpisodesUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTopRatedTvShowsUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTvShowCastUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTvShowDetailUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTvShowVideosUseCaseImpl
import dev.brunofelix.movies.domain.use_case.GetPopularTvShowsUseCase
import dev.brunofelix.movies.domain.use_case.GetSeasonEpisodesUseCase
import dev.brunofelix.movies.domain.use_case.GetTopRatedTvShowsUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowCastUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowDetailUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowVideosUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class TvShowUseCaseModule {

    @Binds
    abstract fun bindGetPopularTvShowsUseCase(
        impl: GetPopularTvShowsUseCaseImpl
    ): GetPopularTvShowsUseCase

    @Binds
    abstract fun bindGetSeasonEpisodesUseCase(
        impl: GetSeasonEpisodesUseCaseImpl
    ): GetSeasonEpisodesUseCase

    @Binds
    abstract fun bindGetTopRatedTvShowsUseCase(
        impl: GetTopRatedTvShowsUseCaseImpl
    ): GetTopRatedTvShowsUseCase

    @Binds
    abstract fun bindGetTvShowCastUseCase(
        impl: GetTvShowCastUseCaseImpl
    ): GetTvShowCastUseCase

    @Binds
    abstract fun bindGetTvShowDetailUseCase(
        impl: GetTvShowDetailUseCaseImpl
    ): GetTvShowDetailUseCase

    @Binds
    abstract fun bindGetTvShowVideosUseCase(
        impl: GetTvShowVideosUseCaseImpl
    ): GetTvShowVideosUseCase
}
