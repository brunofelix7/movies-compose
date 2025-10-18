package dev.brunofelix.movies.feature.favorite.presentation.viewmodel.state

import androidx.annotation.StringRes
import dev.brunofelix.movies.core.domain.model.Movie

sealed class FavoriteUiState{
    data object Initial : FavoriteUiState()
    data object Loading : FavoriteUiState()
    data class Success(val movies: List<Movie>? = emptyList()) : FavoriteUiState()
    data class Error(@StringRes val messageRes: Int) : FavoriteUiState()
}