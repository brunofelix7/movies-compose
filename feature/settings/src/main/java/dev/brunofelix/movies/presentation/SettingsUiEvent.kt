package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.presentation.util.UiText

sealed interface SettingsUiEvent {
    data object NavigateBack : SettingsUiEvent
    data class ShowToast(val message: UiText) : SettingsUiEvent
}
