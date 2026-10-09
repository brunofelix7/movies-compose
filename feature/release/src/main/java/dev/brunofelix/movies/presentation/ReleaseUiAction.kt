package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.MediaListCategory

sealed interface ReleaseUiAction {
    data class OnMonthSelected(val month: ReleaseMonth) : ReleaseUiAction
    data object OnRetry : ReleaseUiAction
    data class OnMediaClick(val media: Media) : ReleaseUiAction
    data class OnViewMoreClick(val category: MediaListCategory) : ReleaseUiAction
}
