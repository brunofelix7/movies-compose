package dev.brunofelix.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.ReleaseType
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieReleasesUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowReleasesUseCase
import dev.brunofelix.movies.domain.util.extension.toMovieMediaList
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
private const val MONTHS_AHEAD = 6

/**
 * Release calendar: the theatrical, streaming and series premieres of the selected month.
 */
@HiltViewModel
class ReleaseViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getMovieReleasesUseCase: GetMovieReleasesUseCase,
    private val getTvShowReleasesUseCase: GetTvShowReleasesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ReleaseUiState(months = ReleaseMonth.window(monthsBack = 0, monthsForward = MONTHS_AHEAD))
    )
    val uiState: StateFlow<ReleaseUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ReleaseUiEvent>()
    val uiEvent: Flow<ReleaseUiEvent> = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            getLanguageUseCase().distinctUntilChanged().collectLatest {
                load(_uiState.value.selectedMonth)
            }
        }
    }

    fun onAction(action: ReleaseUiAction) {
        when (action) {
            is ReleaseUiAction.OnMonthSelected -> selectMonth(action.month)
            ReleaseUiAction.OnRetry -> viewModelScope.launch { load(_uiState.value.selectedMonth) }
            is ReleaseUiAction.OnMediaClick -> sendEvent(ReleaseUiEvent.NavigateToDetails(action.media))
            is ReleaseUiAction.OnViewMoreClick -> sendEvent(
                ReleaseUiEvent.NavigateToMediaList(action.category, _uiState.value.selectedMonth.id)
            )
        }
    }

    private fun selectMonth(month: ReleaseMonth) {
        if (_uiState.value.selectedMonth == month) return
        _uiState.update { it.copy(selectedMonth = month) }
        viewModelScope.launch { load(month) }
    }

    private fun sendEvent(event: ReleaseUiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    private suspend fun load(month: ReleaseMonth) = coroutineScope {
        _uiState.update {
            it.copy(
                theaters = UiState.Loading,
                streaming = UiState.Loading,
                series = UiState.Loading
            )
        }
        launch {
            val result = getMovieReleasesUseCase(month, ReleaseType.THEATERS, FIRST_PAGE)
                .toUiState { movies -> movies.toMovieMediaList() }
            _uiState.update { it.copy(theaters = result) }
        }
        launch {
            val result = getMovieReleasesUseCase(month, ReleaseType.STREAMING, FIRST_PAGE)
                .toUiState { movies -> movies.toMovieMediaList() }
            _uiState.update { it.copy(streaming = result) }
        }
        launch {
            val result = getTvShowReleasesUseCase(month, FIRST_PAGE)
                .toUiState { tvShows -> tvShows.toTvShowMediaList() }
            _uiState.update { it.copy(series = result) }
        }
    }
}
