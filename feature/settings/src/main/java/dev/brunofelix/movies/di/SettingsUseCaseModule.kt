package dev.brunofelix.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.domain.use_case.GetAppVersionUseCase
import dev.brunofelix.movies.domain.use_case.GetAppVersionUseCaseImpl
import dev.brunofelix.movies.domain.use_case.SaveLanguageUseCase
import dev.brunofelix.movies.domain.use_case.SaveLanguageUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsUseCaseModule {

    @Binds
    abstract fun bindGetAppVersionUseCase(
        impl: GetAppVersionUseCaseImpl
    ): GetAppVersionUseCase

    @Binds
    abstract fun bindSaveLanguageUseCase(
        impl: SaveLanguageUseCaseImpl
    ): SaveLanguageUseCase
}
