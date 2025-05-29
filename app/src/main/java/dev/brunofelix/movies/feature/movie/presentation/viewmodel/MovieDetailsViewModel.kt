package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.util.exception.RemoteException
import dev.brunofelix.movies.feature.movie.domain.use_case.DeleteFromFavoritesUseCase
import dev.brunofelix.movies.feature.movie.domain.use_case.GetMovieDetailsUseCase
import dev.brunofelix.movies.feature.movie.domain.use_case.IsFavoriteMovieUseCase
import dev.brunofelix.movies.feature.movie.domain.use_case.MarkAsFavoriteUseCase
import dev.brunofelix.movies.feature.movie.presentation.state.MovieDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val markAsFavoriteUseCase: MarkAsFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteMovieUseCase,
    private val deleteFavoriteUseCase: DeleteFromFavoritesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Initial)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    fun getDetails(movieId: Long) = viewModelScope.launch {
        _uiState.value = MovieDetailsUiState.Loading
        try {
            getMovieDetailsUseCase.invoke(movieId)?.let {
                _uiState.value = MovieDetailsUiState.Success(
                    movie = it,
                    isFavorite = isFavoriteUseCase.invoke(movieId)
                )
            } ?: run {
                _uiState.value = MovieDetailsUiState.Error(R.string.movie_details_error)
            }
        } catch (e: RemoteException) {
            _uiState.value = MovieDetailsUiState.Error(e.messageRes)
        }
    }

    fun onFavoriteToggle() = viewModelScope.launch {
        try {
            val currentState = uiState.value
            if (currentState is MovieDetailsUiState.Success) {
                currentState.movie?.let { movie ->
                    if (currentState.isFavorite) {
                        deleteFavoriteUseCase.invoke(movie)
                    } else {
                        markAsFavoriteUseCase.invoke(movie)
                    }
                    _uiState.value = MovieDetailsUiState.Success(
                        movie = movie,
                        isFavorite = isFavoriteUseCase.invoke(movie.id)
                    )
                }
            }
        } catch (_: RemoteException) {
            _uiState.value = MovieDetailsUiState.Error(R.string.mark_favorite_error)
        }
    }
}