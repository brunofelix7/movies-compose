package dev.brunofelix.movies.feature.detail.presentation.state

import androidx.compose.runtime.Immutable
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState

@Immutable
data class DetailUiState(
    val state: UiState<MovieUiState> = UiState.Loading,
    val isFavorite: Boolean = false,
    val onBack: () -> Unit = {},
    val onFavorite: () -> Unit = {}
)
