package dev.brunofelix.movies.presentation.detail

import dev.brunofelix.movies.presentation.model.TvShowUiModel
import dev.brunofelix.movies.presentation.util.UiState

data class TvShowDetailUiState(
    val tvShow: UiState<TvShowUiModel> = UiState.Initial,
    val isFavorite: Boolean = false,
    val seasons: SeasonsState = SeasonsState()
)
