package dev.brunofelix.movies.presentation.home

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.presentation.util.UiState

data class MovieHomeUiState(
    val popular: UiState<List<Media>> = UiState.Initial,
    val upcoming: UiState<List<Media>> = UiState.Initial,
    val topRated: UiState<List<Media>> = UiState.Initial
)
