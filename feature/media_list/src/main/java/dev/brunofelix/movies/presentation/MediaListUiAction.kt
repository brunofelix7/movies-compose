package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.MediaListCategory

sealed interface MediaListUiAction {
    data class OnLoad(val category: MediaListCategory, val month: ReleaseMonth?) : MediaListUiAction
    data class OnMediaClick(val media: Media) : MediaListUiAction
    data object OnBack : MediaListUiAction
}
