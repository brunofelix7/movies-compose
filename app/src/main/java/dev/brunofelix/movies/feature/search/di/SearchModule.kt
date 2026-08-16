package dev.brunofelix.movies.feature.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.search.data.repository.SearchRepositoryImpl
import dev.brunofelix.movies.feature.search.domain.repository.SearchRepository
import dev.brunofelix.movies.feature.search.domain.use_case.SearchUseCase
import dev.brunofelix.movies.feature.search.domain.use_case.SearchUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    abstract fun bindSearchUseCase(
        impl: SearchUseCaseImpl
    ): SearchUseCase
}
