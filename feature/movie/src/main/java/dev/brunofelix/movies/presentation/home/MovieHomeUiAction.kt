package dev.brunofelix.movies.presentation.home

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory

sealed interface MovieHomeUiAction {
    data object OnRetry : MovieHomeUiAction
    data class OnMediaClick(val media: Media) : MovieHomeUiAction
    data class OnViewMoreClick(val category: MediaListCategory) : MovieHomeUiAction
}
