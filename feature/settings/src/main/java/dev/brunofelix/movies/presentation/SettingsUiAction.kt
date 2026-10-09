package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.enums.LanguageEnum

sealed interface SettingsUiAction {
    data object OnBack : SettingsUiAction
    data class OnLanguageSelected(val language: LanguageEnum) : SettingsUiAction
}
