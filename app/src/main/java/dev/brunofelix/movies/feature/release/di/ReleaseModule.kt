package dev.brunofelix.movies.feature.release.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.release.domain.use_case.GetMovieReleasesUseCase
import dev.brunofelix.movies.feature.release.domain.use_case.GetMovieReleasesUseCaseImpl
import dev.brunofelix.movies.feature.release.domain.use_case.GetTvShowReleasesUseCase
import dev.brunofelix.movies.feature.release.domain.use_case.GetTvShowReleasesUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ReleaseModule {

    @Binds
    abstract fun bindGetMovieReleasesUseCase(
        impl: GetMovieReleasesUseCaseImpl
    ): GetMovieReleasesUseCase

    @Binds
    abstract fun bindGetTvShowReleasesUseCase(
        impl: GetTvShowReleasesUseCaseImpl
    ): GetTvShowReleasesUseCase
}
