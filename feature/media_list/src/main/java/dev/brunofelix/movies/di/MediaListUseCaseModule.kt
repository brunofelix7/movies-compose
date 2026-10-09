package dev.brunofelix.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.domain.use_case.GetMediaListUseCase
import dev.brunofelix.movies.domain.use_case.GetMediaListUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaListUseCaseModule {

    @Binds
    abstract fun bindGetMediaListUseCase(
        impl: GetMediaListUseCaseImpl
    ): GetMediaListUseCase
}
