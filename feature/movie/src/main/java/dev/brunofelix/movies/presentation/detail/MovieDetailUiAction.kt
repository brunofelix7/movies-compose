package dev.brunofelix.movies.presentation.detail

sealed interface MovieDetailUiAction {
    data class OnLoad(val movieId: Long) : MovieDetailUiAction
    data object OnRetry : MovieDetailUiAction
    data object OnFavoriteToggle : MovieDetailUiAction
    data object OnBack : MovieDetailUiAction
}
