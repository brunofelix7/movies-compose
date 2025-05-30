package dev.brunofelix.movies.feature.details.presentation.viewmodel.state

import androidx.annotation.StringRes
import dev.brunofelix.movies.core.domain.model.Movie

sealed class MovieDetailsUiState {
    data object Initial : MovieDetailsUiState()
    data object Loading : MovieDetailsUiState()
    data class Success(
        val movie: Movie? = null,
        val isFavorite: Boolean = false
    ) : MovieDetailsUiState()
    data class Error(@StringRes val messageRes: Int) : MovieDetailsUiState()
}