package dev.brunofelix.movies.core.presentation.ui.state

import androidx.annotation.StringRes

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(@get:StringRes val messageRes: Int) : UiState<Nothing>()
    data object Empty : UiState<Nothing>()
}