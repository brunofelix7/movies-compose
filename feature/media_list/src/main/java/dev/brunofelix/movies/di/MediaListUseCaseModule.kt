package dev.brunofelix.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.data.use_case.GetMediaListUseCaseImpl
import dev.brunofelix.movies.domain.use_case.GetMediaListUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaListUseCaseModule {

    @Binds
    abstract fun bindGetMediaListUseCase(
        impl: GetMediaListUseCaseImpl
    ): GetMediaListUseCase
}
