package dev.brunofelix.movies.feature.media_list.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.media_list.domain.use_case.GetMediaListUseCase
import dev.brunofelix.movies.feature.media_list.domain.use_case.GetMediaListUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaListModule {

    @Binds
    abstract fun bindGetMediaListUseCase(
        impl: GetMediaListUseCaseImpl
    ): GetMediaListUseCase
}
