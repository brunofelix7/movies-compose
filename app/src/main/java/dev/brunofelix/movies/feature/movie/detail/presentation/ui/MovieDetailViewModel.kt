package dev.brunofelix.movies.feature.movie.detail.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.presentation.util.extension.toUiText
import dev.brunofelix.movies.core.domain.mapper.toMedia
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.presentation.mapper.toUiModel
import dev.brunofelix.movies.core.presentation.ui.model.MovieUiModel
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieCastUseCase
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieDetailUseCase
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieVideosUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val saveMediaUseCase: SaveMediaUseCase,
    private val isFavoriteMediaUseCase: IsFavoriteMediaUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MovieUiModel>>(UiState.Initial)
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

            val detailsDeferred = async { getMovieDetailUseCase(movieId) }
            val videosDeferred = async { getMovieVideosUseCase(movieId) }
            val castDeferred = async { getMovieCastUseCase(movieId) }

            val detailsResult = detailsDeferred.await()
            val videosResult = videosDeferred.await()
            val castResult = castDeferred.await()

            if (detailsResult is Resource.Success) {
                movieDomain = detailsResult.data
                val trailerKey = if (videosResult is Resource.Success) {
                    videosResult.data.find {
                        it.site.equals("YouTube", ignoreCase = true) &&
                                it.type.equals("Trailer", ignoreCase = true)
                    }?.key ?: videosResult.data.firstOrNull {
                        it.site.equals("YouTube", ignoreCase = true)
                    }?.key
                } else null
                val cast = if (castResult is Resource.Success) {
                    castResult.data.map { it.toUiModel() }
                } else emptyList()

                _uiState.value = UiState.Success(
                    detailsResult.data.toUiModel().copy(trailerKey = trailerKey, cast = cast)
                )
                _isFavorite.value = isFavoriteMediaUseCase(detailsResult.data.id)
            } else if (detailsResult is Resource.Error) {
                _uiState.value = UiState.Error(detailsResult.throwable.toUiText())
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