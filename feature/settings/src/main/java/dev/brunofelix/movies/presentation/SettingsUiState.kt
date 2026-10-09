package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.enums.LanguageEnum

data class SettingsUiState(
    val languages: List<LanguageEnum> = LanguageEnum.entries,
    val selectedLanguage: LanguageEnum = LanguageEnum.ENGLISH,
    val appVersion: String = ""
)
