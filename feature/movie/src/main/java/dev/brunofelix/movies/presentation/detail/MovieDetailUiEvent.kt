package dev.brunofelix.movies.presentation.detail

import dev.brunofelix.movies.presentation.util.UiText

sealed interface MovieDetailUiEvent {
    data object NavigateBack : MovieDetailUiEvent
    data class ShowToast(val message: UiText) : MovieDetailUiEvent
}
