package dev.brunofelix.movies.feature.movie.presentation.state

import androidx.annotation.StringRes

sealed class MovieFavoriteUiState {
    data object Loading : MovieFavoriteUiState()
    data object Success : MovieFavoriteUiState()
    data class Error(@StringRes val messageRes: Int) : MovieFavoriteUiState()
}