package dev.brunofelix.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.domain.use_case.SearchMediaUseCase
import dev.brunofelix.movies.domain.use_case.SearchMediaUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchUseCaseModule {

    @Binds
    abstract fun bindSearchMediaUseCase(
        impl: SearchMediaUseCaseImpl
    ): SearchMediaUseCase
}
