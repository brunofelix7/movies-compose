package dev.brunofelix.movies.core.presentation.components.card

sealed class MovieCardState {
    data object Loading : MovieCardState()
    data object Success : MovieCardState()
    data object Error : MovieCardState()
}