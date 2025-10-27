package dev.brunofelix.movies.feature.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.ui.state.UiState
import dev.brunofelix.movies.feature.details.domain.use_case.DeleteMovieUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.GetMovieDetailsUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.IsFavoriteUseCase
import dev.brunofelix.movies.feature.details.domain.use_case.SaveMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val saveUseCase: SaveMovieUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val deleteUseCase: DeleteMovieUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Movie>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun getDetails(movieId: Long) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        when (val result = getMovieDetailsUseCase(movieId)) {
            is Resource.Success -> {
                _uiState.value = UiState.Success(result.data)
                _isFavorite.value = isFavoriteUseCase(result.data.id)
            }
            is Resource.Error -> {
                _uiState.value = UiState.Error(R.string.movie_details_error)
            }
        }
    }

    fun onFavoriteToggle() = viewModelScope.launch {
        (uiState.value as? UiState.Success)?.data?.let { movie ->
            if (_isFavorite.value) deleteUseCase(movie) else saveUseCase(movie)
            _isFavorite.value = isFavoriteUseCase(movie.id)
        }
    }
}