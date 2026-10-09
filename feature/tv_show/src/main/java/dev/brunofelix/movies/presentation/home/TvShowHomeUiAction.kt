package dev.brunofelix.movies.presentation.home

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory

sealed interface TvShowHomeUiAction {
    data object OnRetry : TvShowHomeUiAction
    data class OnMediaClick(val media: Media) : TvShowHomeUiAction
    data class OnViewMoreClick(val category: MediaListCategory) : TvShowHomeUiAction
}
