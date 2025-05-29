package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.util.exception.RemoteException
import dev.brunofelix.movies.feature.movie.domain.model.Movie
import dev.brunofelix.movies.feature.movie.domain.use_case.DeleteFromFavoritesUseCase
import dev.brunofelix.movies.feature.movie.domain.use_case.GetMovieDetailsUseCase
import dev.brunofelix.movies.feature.movie.domain.use_case.IsFavoriteMovieUseCase
import dev.brunofelix.movies.feature.movie.domain.use_case.MarkAsFavoriteUseCase
import dev.brunofelix.movies.feature.movie.presentation.state.MovieDetailsUiState
import dev.brunofelix.movies.feature.movie.presentation.state.MovieFavoriteUiState
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

    fun getDetails(movieId: Long) = viewModelScope.launch {
        _uiState.value = MovieDetailsUiState.Loading
        try {
            getMovieDetailsUseCase.invoke(movieId)?.let {
                _uiState.value = MovieDetailsUiState.Success(it)
            } ?: run {
                _uiState.value = MovieDetailsUiState.Error(R.string.movie_details_error)
            }
        } catch (e: RemoteException) {
            _uiState.value = MovieDetailsUiState.Error(e.messageRes)
        }
    }

    private val _movieFavoriteUiState = MutableLiveData<MovieFavoriteUiState>()
    val movieFavoriteUiState: LiveData<MovieFavoriteUiState> = _movieFavoriteUiState

    fun markAsFavorite(movie: Movie) = viewModelScope.launch {
        _movieFavoriteUiState.value = MovieFavoriteUiState.Loading
        try {
            markAsFavoriteUseCase.invoke(movie)
            _movieFavoriteUiState.value = MovieFavoriteUiState.Success
            checkIsFavorite(movie.id)
        } catch (e: RemoteException) {
            _movieFavoriteUiState.value = MovieFavoriteUiState.Error(e.messageRes)
        }
    }

    private val _isFavoriteUiState = MutableLiveData<Boolean>()
    val isFavoriteUiState: LiveData<Boolean> = _isFavoriteUiState

    fun checkIsFavorite(movieId: Long) = viewModelScope.launch {
        try {
            _isFavoriteUiState.value = isFavoriteUseCase.invoke(movieId)
        } catch (e: RemoteException) {
            _isFavoriteUiState.value = false
        }
    }

    fun removeFavorite(movie: Movie) = viewModelScope.launch {
        try {
            deleteFavoriteUseCase.invoke(movie)
            _isFavoriteUiState.value = false
        } catch (e: RemoteException) {
            _isFavoriteUiState.value = false
        }
    }
}