package dev.brunofelix.movies.feature.favorite.presentation.state

import androidx.compose.runtime.Immutable
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.state.UiState

@Immutable
data class MovieFavoriteState(
    val uiState: UiState<List<Media>> = UiState.Loading,
    val onCardClick: (Long) -> Unit = {}
)