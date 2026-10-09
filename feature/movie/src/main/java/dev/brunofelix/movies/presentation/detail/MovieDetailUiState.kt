package dev.brunofelix.movies.presentation.detail

import dev.brunofelix.movies.presentation.model.MovieUiModel
import dev.brunofelix.movies.presentation.util.UiState

data class MovieDetailUiState(
    val movie: UiState<MovieUiModel> = UiState.Initial,
    val isFavorite: Boolean = false
)
