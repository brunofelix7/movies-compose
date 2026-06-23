package dev.brunofelix.movies.core.presentation.state

sealed class MovieCardUiState {
    data object Loading : MovieCardUiState()
    data object Success : MovieCardUiState()
    data object Error : MovieCardUiState()
}