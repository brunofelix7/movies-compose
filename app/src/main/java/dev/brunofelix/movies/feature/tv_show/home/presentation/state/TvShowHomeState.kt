package dev.brunofelix.movies.feature.tv_show.home.presentation.state

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.util.UiState

data class TvShowHomeState(
    val popular: UiState<List<Media>> = UiState.Initial,
    val topRated: UiState<List<Media>> = UiState.Initial
)
