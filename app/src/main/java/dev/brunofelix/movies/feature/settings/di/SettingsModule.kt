package dev.brunofelix.movies.feature.settings.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.feature.settings.domain.use_case.SaveLanguageUseCase
import dev.brunofelix.movies.feature.settings.domain.use_case.SaveLanguageUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindSaveLanguageUseCase(
        impl: SaveLanguageUseCaseImpl
    ): SaveLanguageUseCase
}
