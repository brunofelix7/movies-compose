package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media

sealed interface SearchUiEvent {
    data object Close : SearchUiEvent
    data class NavigateToDetails(val media: Media) : SearchUiEvent
}
