package dev.brunofelix.movies.feature.tv_show.detail.presentation.state

import dev.brunofelix.movies.core.presentation.ui.model.EpisodeUiModel
import dev.brunofelix.movies.core.presentation.util.UiState

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
