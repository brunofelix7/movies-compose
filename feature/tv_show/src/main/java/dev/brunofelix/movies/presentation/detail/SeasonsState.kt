package dev.brunofelix.movies.presentation.detail

import dev.brunofelix.movies.presentation.model.EpisodeUiModel
import dev.brunofelix.movies.presentation.util.UiState

/**
 * Episodes are fetched one season at a time, when the user expands it, so each season keeps
 * its own state and only one can be open at a time.
 */
data class SeasonsState(
    val expandedSeasonNumber: Int? = null,
    val episodes: Map<Int, UiState<List<EpisodeUiModel>>> = emptyMap()
) {
    fun episodesOf(seasonNumber: Int): UiState<List<EpisodeUiModel>> {
        return episodes[seasonNumber] ?: UiState.Initial
    }
}
