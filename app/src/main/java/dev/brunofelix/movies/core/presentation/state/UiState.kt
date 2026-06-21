package dev.brunofelix.movies.core.presentation.state

import androidx.annotation.StringRes

sealed class UiState<out T> {
    data object Initial : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(@get:StringRes val messageRes: Int) : UiState<Nothing>()
}