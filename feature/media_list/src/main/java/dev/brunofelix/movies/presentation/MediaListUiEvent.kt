package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media

sealed interface MediaListUiEvent {
    data object NavigateBack : MediaListUiEvent
    data class NavigateToDetails(val media: Media) : MediaListUiEvent
}
