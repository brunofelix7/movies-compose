package dev.brunofelix.movies.feature.settings.presentation.state

import dev.brunofelix.movies.BuildConfig
import dev.brunofelix.movies.core.domain.model.enums.LanguageEnum

data class SettingsState(
    val languages: List<LanguageEnum> = LanguageEnum.entries,
    val selectedLanguage: LanguageEnum = LanguageEnum.ENGLISH,
    val appVersion: String = BuildConfig.VERSION_NAME
)
