package dev.brunofelix.movies.feature.tv_show.detail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.tv_show.detail.domain.use_case.GetTvShowDetailUseCase
import dev.brunofelix.movies.feature.tv_show.detail.domain.use_case.GetTvShowDetailUseCaseImpl
import dev.brunofelix.movies.feature.tv_show.detail.domain.use_case.GetTvShowVideosUseCase
import dev.brunofelix.movies.feature.tv_show.detail.domain.use_case.GetTvShowVideosUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class TvShowDetailModule {

    @Binds
    abstract fun bindGetTvShowDetailUseCase(
        impl: GetTvShowDetailUseCaseImpl
    ): GetTvShowDetailUseCase

    @Binds
    abstract fun bindGetTvShowVideosUseCase(
        impl: GetTvShowVideosUseCaseImpl
    ): GetTvShowVideosUseCase
}
