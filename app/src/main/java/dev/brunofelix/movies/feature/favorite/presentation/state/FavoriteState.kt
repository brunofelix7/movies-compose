package dev.brunofelix.movies.feature.favorite.presentation.state

import androidx.compose.runtime.Immutable
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.state.UiState

@Immutable
data class FavoriteState(
    val uiState: UiState<List<Movie>> = UiState.Loading,
    val onCardClick: (Long) -> Unit = {}
)
