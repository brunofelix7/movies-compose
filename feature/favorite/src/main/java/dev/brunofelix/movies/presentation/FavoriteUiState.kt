package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.presentation.model.MediaUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.model.FavoriteCategory

/**
 * @property providers Streaming services that offer at least one favorite of the selected
 * category, the most common first. The streaming filter is hidden when it is empty.
 * @property selectedProviderId Service the list is filtered by, `null` for every favorite.
 */
data class FavoriteUiState(
    val selectedCategory: FavoriteCategory = FavoriteCategory.MOVIES,
    val providers: List<WatchProvider> = emptyList(),
    val selectedProviderId: Long? = null,
    val medias: UiState<List<MediaUiModel>> = UiState.Initial
)
