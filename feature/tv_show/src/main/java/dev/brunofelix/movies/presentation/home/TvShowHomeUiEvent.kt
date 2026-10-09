package dev.brunofelix.movies.presentation.home

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory

sealed interface TvShowHomeUiEvent {
    data class NavigateToDetails(val media: Media) : TvShowHomeUiEvent
    data class NavigateToMediaList(val category: MediaListCategory) : TvShowHomeUiEvent
}
