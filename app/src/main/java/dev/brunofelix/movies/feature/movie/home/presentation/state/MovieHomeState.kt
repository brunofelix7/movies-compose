package dev.brunofelix.movies.feature.movie.home.presentation.state

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.util.UiState

data class MovieHomeState(
    val popular: UiState<List<Media>> = UiState.Initial,
    val upcoming: UiState<List<Media>> = UiState.Initial,
    val topRated: UiState<List<Media>> = UiState.Initial
)
