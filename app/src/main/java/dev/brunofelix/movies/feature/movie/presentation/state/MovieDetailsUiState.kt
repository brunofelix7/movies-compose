package dev.brunofelix.movies.feature.movie.presentation.state

import androidx.annotation.StringRes
import dev.brunofelix.movies.feature.movie.domain.model.Movie

sealed class MovieDetailsUiState {
    data object Loading : MovieDetailsUiState()
    data class Success(val movie: Movie? = null) : MovieDetailsUiState()
    data class Error(@StringRes val messageRes: Int) : MovieDetailsUiState()
}