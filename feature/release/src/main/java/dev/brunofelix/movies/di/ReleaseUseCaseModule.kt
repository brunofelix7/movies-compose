package dev.brunofelix.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.data.use_case.GetMovieReleasesUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTvShowReleasesUseCaseImpl
import dev.brunofelix.movies.domain.use_case.GetMovieReleasesUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowReleasesUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class ReleaseUseCaseModule {

    @Binds
    abstract fun bindGetMovieReleasesUseCase(
        impl: GetMovieReleasesUseCaseImpl
    ): GetMovieReleasesUseCase

    @Binds
    abstract fun bindGetTvShowReleasesUseCase(
        impl: GetTvShowReleasesUseCaseImpl
    ): GetTvShowReleasesUseCase
}
