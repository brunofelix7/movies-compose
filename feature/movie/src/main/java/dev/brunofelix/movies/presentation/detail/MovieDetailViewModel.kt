package dev.brunofelix.movies.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieCastUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieDetailUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieVideosUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieWatchProvidersUseCase
import dev.brunofelix.movies.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.domain.use_case.UpdateFavoriteWatchProvidersUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.extension.toMedia
import dev.brunofelix.movies.domain.util.extension.toWatchAvailability
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.mapper.toTrailerKey
import dev.brunofelix.movies.presentation.mapper.toUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.util.extension.toUiText
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieWatchProvidersUseCase: GetMovieWatchProvidersUseCase,
    private val saveMediaUseCase: SaveMediaUseCase,
    private val isFavoriteMediaUseCase: IsFavoriteMediaUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase,
    private val updateFavoriteWatchProvidersUseCase: UpdateFavoriteWatchProvidersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MovieDetailUiEvent>()
    val uiEvent: Flow<MovieDetailUiEvent> = _uiEvent.receiveAsFlow()

    private var movieId: Long? = null
    private var movie: Movie? = null

    /** `null` when they could not be loaded, so a favorite saved now gets them on the next sync. */
    private var watchProviders: List<WatchProvider>? = null

    fun onAction(action: MovieDetailUiAction) {
        when (action) {
            is MovieDetailUiAction.OnLoad -> load(action.movieId)
            MovieDetailUiAction.OnRetry -> movieId?.let { load(it, force = true) }
            MovieDetailUiAction.OnFavoriteToggle -> toggleFavorite()
            MovieDetailUiAction.OnBack -> viewModelScope.launch { _uiEvent.send(MovieDetailUiEvent.NavigateBack) }
        }
    }

    private fun load(id: Long, force: Boolean = false) {
        if (!force && movieId == id && _uiState.value.movie !is UiState.Error) return
        movieId = id

        viewModelScope.launch {
            _uiState.value = MovieDetailUiState(movie = UiState.Loading)

            val details = async { getMovieDetailUseCase(id) }
            val videos = async { getMovieVideosUseCase(id) }
            val cast = async { getMovieCastUseCase(id) }
            val providers = async { getMovieWatchProvidersUseCase(id) }

            when (val result = details.await()) {
                is Resource.Success -> {
                    movie = result.data
                    watchProviders = (providers.await() as? Resource.Success)?.data
                    val trailerKey = (videos.await() as? Resource.Success<List<Video>>)?.data?.toTrailerKey()
                    val castList = (cast.await() as? Resource.Success)?.data.orEmpty().map { it.toUiModel() }
                    val availability = watchProviders?.let { result.data.toWatchAvailability(it) }
                    _uiState.update {
                        it.copy(
                            movie = UiState.Success(
                                result.data.toUiModel().copy(
                                    trailerKey = trailerKey,
                                    cast = castList,
                                    watchAvailability = availability
                                )
                            )
                        )
                    }
                    refreshFavorite(result.data.id)
                    refreshStoredWatchProviders(result.data.id)
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(movie = UiState.Error(result.throwable.toUiText())) }
                }
            }
        }
    }

    private fun toggleFavorite() {
        val media = movie?.toMedia()?.copy(watchProviders = watchProviders) ?: return
        viewModelScope.launch {
            val result = if (_uiState.value.isFavorite) deleteMediaUseCase(media) else saveMediaUseCase(media)
            if (result is Resource.Error) {
                val message = if (_uiState.value.isFavorite) R.string.delete_media_error else R.string.mark_favorite_error
                _uiEvent.send(MovieDetailUiEvent.ShowToast(UiText.StringResource(message)))
            }
            refreshFavorite(media.id)
        }
    }

    private suspend fun refreshFavorite(id: Long) {
        when (val result = isFavoriteMediaUseCase(id)) {
            is Resource.Success -> _uiState.update { it.copy(isFavorite = result.data) }
            is Resource.Error -> _uiEvent.send(
                MovieDetailUiEvent.ShowToast(UiText.StringResource(R.string.is_favorite_media_error))
            )
        }
    }

    /**
     * Keeps the streaming services of a favorite current, which is what the favorites filter
     * reads. A failure is silent: the stored ones stay until the next visit.
     */
    private suspend fun refreshStoredWatchProviders(id: Long) {
        val providers = watchProviders ?: return
        if (_uiState.value.isFavorite) updateFavoriteWatchProvidersUseCase(id, providers)
    }
}
