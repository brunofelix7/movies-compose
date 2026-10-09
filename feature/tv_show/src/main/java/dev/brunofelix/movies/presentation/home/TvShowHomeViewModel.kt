package dev.brunofelix.movies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetPopularTvShowsUseCase
import dev.brunofelix.movies.domain.use_case.GetTopRatedTvShowsUseCase
import dev.brunofelix.movies.domain.util.extension.toTvShowMediaList
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
 * Loads the two rows of the TV shows tab, again whenever the language changes.
 */
@HiltViewModel
class TvShowHomeViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TvShowHomeUiState())
    val uiState: StateFlow<TvShowHomeUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<TvShowHomeUiEvent>()
    val uiEvent: Flow<TvShowHomeUiEvent> = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            getLanguageUseCase().distinctUntilChanged().collectLatest { load() }
        }
    }

    fun onAction(action: TvShowHomeUiAction) {
        when (action) {
            TvShowHomeUiAction.OnRetry -> viewModelScope.launch { load() }
            is TvShowHomeUiAction.OnMediaClick -> sendEvent(TvShowHomeUiEvent.NavigateToDetails(action.media))
            is TvShowHomeUiAction.OnViewMoreClick -> sendEvent(TvShowHomeUiEvent.NavigateToMediaList(action.category))
        }
    }

    private fun sendEvent(event: TvShowHomeUiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    private suspend fun load() = coroutineScope {
        _uiState.value = TvShowHomeUiState(
            popular = UiState.Loading,
            topRated = UiState.Loading
        )
        launch {
            val result = getPopularTvShowsUseCase(FIRST_PAGE).toUiState { it.toTvShowMediaList() }
            _uiState.update { it.copy(popular = result) }
        }
        launch {
            val result = getTopRatedTvShowsUseCase(FIRST_PAGE).toUiState { it.toTvShowMediaList() }
            _uiState.update { it.copy(topRated = result) }
        }
    }
}
