package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory

sealed interface ReleaseUiEvent {
    data class NavigateToDetails(val media: Media) : ReleaseUiEvent

    /**
     * @param monthId `yyyy-MM` of the month the list belongs to.
     */
    data class NavigateToMediaList(val category: MediaListCategory, val monthId: String) : ReleaseUiEvent
}
