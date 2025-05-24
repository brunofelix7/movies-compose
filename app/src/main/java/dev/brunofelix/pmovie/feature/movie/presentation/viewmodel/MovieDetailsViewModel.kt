package dev.brunofelix.pmovie.feature.movie.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.pmovie.R
import dev.brunofelix.pmovie.core.util.exception.RemoteException
import dev.brunofelix.pmovie.feature.movie.domain.use_case.GetMovieDetailsUseCase
import dev.brunofelix.pmovie.feature.movie.presentation.state.MovieDetailsUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCase: GetMovieDetailsUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<MovieDetailsUiState>()
    val uiState: LiveData<MovieDetailsUiState> = _uiState

    fun getDetails(movieId: Long) = viewModelScope.launch {
        _uiState.value = MovieDetailsUiState.Loading
        try {
            useCase(movieId)?.let {
                _uiState.value = MovieDetailsUiState.Success(it)
            } ?: run {
                _uiState.value = MovieDetailsUiState.Error(R.string.movie_details_error)
            }
        } catch (e: RemoteException) {
            _uiState.value = MovieDetailsUiState.Error(e.messageRes)
        }
    }
}