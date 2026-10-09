package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.presentation.util.UiText

sealed interface FavoriteUiEvent {
    data class NavigateToDetails(val media: Media) : FavoriteUiEvent
    data class ShowToast(val message: UiText) : FavoriteUiEvent
}
