package dev.brunofelix.movies.feature.movie.presentation.state

sealed class MovieItemUiState {
    data object Loading : MovieItemUiState()
    data object Success : MovieItemUiState()
    data object Error : MovieItemUiState()
}