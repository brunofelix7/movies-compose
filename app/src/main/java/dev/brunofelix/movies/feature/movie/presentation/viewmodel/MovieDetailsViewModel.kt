package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val markAsFavoriteUseCase: MarkAsFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteMovieUseCase,
    private val deleteFavoriteUseCase: DeleteFromFavoritesUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<MovieDetailsUiState>()
    val uiState: LiveData<MovieDetailsUiState> = _uiState

    private val _isFavoriteUiState = MutableLiveData<Boolean>()
    val isFavoriteUiState: LiveData<Boolean> = _isFavoriteUiState

    fun getDetails(movieId: Long) = viewModelScope.launch {
        _uiState.value = MovieDetailsUiState.Loading
        try {
            getMovieDetailsUseCase.invoke(movieId)?.let {
                _uiState.value = MovieDetailsUiState.Success(it)
                _isFavoriteUiState.value = isFavoriteUseCase.invoke(movieId)
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
                    if (isFavoriteUiState.value == true) {
                        deleteFavoriteUseCase.invoke(movie)
                    } else {
                        markAsFavoriteUseCase.invoke(movie)
                    }
                    _isFavoriteUiState.value = isFavoriteUseCase.invoke(movie.id)
                }
            }
        } catch (_: RemoteException) {
            _uiState.value = MovieDetailsUiState.Error(R.string.mark_favorite_error)
        }
    }
}