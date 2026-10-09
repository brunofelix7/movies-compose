package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.presentation.model.MediaUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.model.FavoriteCategory

data class FavoriteUiState(
    val selectedCategory: FavoriteCategory = FavoriteCategory.MOVIES,
    val medias: UiState<List<MediaUiModel>> = UiState.Initial
)
