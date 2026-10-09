package dev.brunofelix.movies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetPopularMoviesUseCase
import dev.brunofelix.movies.domain.use_case.GetTopRatedMoviesUseCase
import dev.brunofelix.movies.domain.use_case.GetUpcomingMoviesUseCase
import dev.brunofelix.movies.domain.util.extension.toMovieMediaList
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.extension.toUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FIRST_PAGE = 1

/**
 * Loads the three rows of the movies tab, again whenever the language changes.
 */
@HiltViewModel
class MovieHomeViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieHomeUiState())
    val uiState: StateFlow<MovieHomeUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MovieHomeUiEvent>()
    val uiEvent: Flow<MovieHomeUiEvent> = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            getLanguageUseCase().distinctUntilChanged().collectLatest { load() }
        }
    }

    fun onAction(action: MovieHomeUiAction) {
        when (action) {
            MovieHomeUiAction.OnRetry -> viewModelScope.launch { load() }
            is MovieHomeUiAction.OnMediaClick -> sendEvent(MovieHomeUiEvent.NavigateToDetails(action.media))
            is MovieHomeUiAction.OnViewMoreClick -> sendEvent(MovieHomeUiEvent.NavigateToMediaList(action.category))
        }
    }

    private fun sendEvent(event: MovieHomeUiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    private suspend fun load() = coroutineScope {
        _uiState.value = MovieHomeUiState(
            popular = UiState.Loading,
            upcoming = UiState.Loading,
            topRated = UiState.Loading
        )
        launch {
            val result = getPopularMoviesUseCase(FIRST_PAGE).toUiState { it.toMovieMediaList() }
            _uiState.update { it.copy(popular = result) }
        }
        launch {
            val result = getUpcomingMoviesUseCase(FIRST_PAGE).toUiState { it.toMovieMediaList() }
            _uiState.update { it.copy(upcoming = result) }
        }
        launch {
            val result = getTopRatedMoviesUseCase(FIRST_PAGE).toUiState { it.toMovieMediaList() }
            _uiState.update { it.copy(topRated = result) }
        }
    }
}
