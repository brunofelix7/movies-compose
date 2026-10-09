package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.presentation.model.MediaUiModel
import dev.brunofelix.movies.presentation.model.FavoriteCategory

sealed interface FavoriteUiAction {
    data class OnCategorySelected(val category: FavoriteCategory) : FavoriteUiAction
    data class OnMediaClick(val media: MediaUiModel) : FavoriteUiAction
    data class OnDelete(val media: MediaUiModel) : FavoriteUiAction
}
