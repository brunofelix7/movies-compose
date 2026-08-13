package dev.brunofelix.movies.core.presentation.state

import dev.brunofelix.movies.core.presentation.util.UiText

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val uiText: UiText) : UiState<Nothing>()
}