package dev.brunofelix.movies.presentation.detail

import dev.brunofelix.movies.presentation.util.UiText

sealed interface TvShowDetailUiEvent {
    data object NavigateBack : TvShowDetailUiEvent
    data class ShowToast(val message: UiText) : TvShowDetailUiEvent
}
