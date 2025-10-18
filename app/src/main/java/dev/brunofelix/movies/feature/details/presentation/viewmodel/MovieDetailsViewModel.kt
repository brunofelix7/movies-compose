package dev.brunofelix.movies.feature.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.util.exception.RemoteException
import dev.brunofelix.movies.feature.details.domain.use_case.DeleteMovieUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.GetDetailsUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.IsFavoriteUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.SaveMovieUseCase
import dev.brunofelix.movies.feature.details.presentation.viewmodel.state.MovieDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val saveUseCase: SaveMovieUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val deleteUseCase: DeleteMovieUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun getDetails(movieId: Long) = viewModelScope.launch {
        _uiState.value = MovieDetailsUiState.Loading
        try {
            getDetailsUseCase.invoke(movieId)?.let {
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
                        deleteUseCase.invoke(movie)
                    } else {
                        saveUseCase.invoke(movie)
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