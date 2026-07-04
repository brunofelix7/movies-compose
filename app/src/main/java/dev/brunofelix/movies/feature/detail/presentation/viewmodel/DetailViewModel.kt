package dev.brunofelix.movies.feature.detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.mapper.toUiState
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.feature.detail.domain.use_case.DeleteMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.GetMovieDetailsUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.IsFavoriteMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.SaveMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val saveMovieUseCase: SaveMovieUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MovieUiState>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private var movieDomain: Movie? = null

    fun getDetails(movieId: Long) {
        val currentState = _uiState.value
        if (currentState is UiState.Success && movieDomain?.id == movieId) {
            return
        }
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _isFavorite.value = false

            when (val result = getMovieDetailsUseCase(movieId)) {
                is Resource.Success -> {
                    movieDomain = result.data
                    _uiState.value = UiState.Success(result.data.toUiState())
                    _isFavorite.value = isFavoriteMovieUseCase(result.data.id)
                }
                is Resource.Error -> {
                    _uiState.value = UiState.Error(R.string.movie_details_error)
                }
            }
        }
    }

    fun onFavoriteToggle() = viewModelScope.launch {
        movieDomain?.let { movie ->
            if (_isFavorite.value) {
                deleteMovieUseCase(movie)
            } else {
                saveMovieUseCase(movie)
            }
            _isFavorite.value = isFavoriteMovieUseCase(movie.id)
        }
    }
}