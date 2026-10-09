package dev.brunofelix.movies.presentation.home

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory

sealed interface MovieHomeUiEvent {
    data class NavigateToDetails(val media: Media) : MovieHomeUiEvent
    data class NavigateToMediaList(val category: MediaListCategory) : MovieHomeUiEvent
}
