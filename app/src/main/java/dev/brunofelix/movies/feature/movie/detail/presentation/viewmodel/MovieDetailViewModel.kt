package dev.brunofelix.movies.feature.movie.detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.data.util.extension.toUiText
import dev.brunofelix.movies.core.domain.mapper.toMedia
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.presentation.mapper.toUiState
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val saveMediaUseCase: SaveMediaUseCase,
    private val isFavoriteMediaUseCase: IsFavoriteMediaUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase
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

            when (val result = getMovieDetailUseCase(movieId)) {
                is Resource.Success -> {
                    movieDomain = result.data
                    _uiState.value = UiState.Success(result.data.toUiState())
                    _isFavorite.value = isFavoriteMediaUseCase(result.data.id)
                }
                is Resource.Error -> {
                    _uiState.value = UiState.Error(result.throwable.toUiText())
                }
            }
        }
    }

    fun onFavoriteToggle() = viewModelScope.launch {
        movieDomain?.let { movie ->
            if (_isFavorite.value) {
                deleteMediaUseCase(movie.toMedia())
            } else {
                saveMediaUseCase(movie.toMedia())
            }
            _isFavorite.value = isFavoriteMediaUseCase(movie.id)
        }
    }
}