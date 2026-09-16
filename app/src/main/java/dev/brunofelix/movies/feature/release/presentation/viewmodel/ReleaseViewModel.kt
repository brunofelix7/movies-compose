package dev.brunofelix.movies.feature.release.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.domain.mapper.toMovieMediaList
import dev.brunofelix.movies.core.domain.mapper.toTvShowMediaList
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.enums.ReleaseType
import dev.brunofelix.movies.core.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.extension.toUiState
import dev.brunofelix.movies.feature.release.domain.use_case.GetMovieReleasesUseCase
import dev.brunofelix.movies.feature.release.domain.use_case.GetTvShowReleasesUseCase
import dev.brunofelix.movies.feature.release.presentation.state.ReleaseState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FIRST_PAGE = 1
private const val MONTHS_AHEAD = 6

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getMovieReleasesUseCase: GetMovieReleasesUseCase,
    private val getTvShowReleasesUseCase: GetTvShowReleasesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        ReleaseState(months = ReleaseMonth.window(monthsBack = 0, monthsForward = MONTHS_AHEAD))
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getLanguageUseCase().distinctUntilChanged().collectLatest {
                load(_state.value.selectedMonth)
            }
        }
    }

    fun onMonthSelected(month: ReleaseMonth) {
        if (_state.value.selectedMonth == month) return
        _state.update { it.copy(selectedMonth = month) }
        viewModelScope.launch { load(month) }
    }

    fun onRetry() {
        viewModelScope.launch { load(_state.value.selectedMonth) }
    }

    private suspend fun load(month: ReleaseMonth) = coroutineScope {
        _state.update {
            it.copy(
                theaters = UiState.Loading,
                streaming = UiState.Loading,
                series = UiState.Loading
            )
        }
        launch {
            val result = getMovieReleasesUseCase(month, ReleaseType.THEATERS, FIRST_PAGE)
                .toUiState { movies -> movies.toMovieMediaList() }
            _state.update { it.copy(theaters = result) }
        }
        launch {
            val result = getMovieReleasesUseCase(month, ReleaseType.STREAMING, FIRST_PAGE)
                .toUiState { movies -> movies.toMovieMediaList() }
            _state.update { it.copy(streaming = result) }
        }
        launch {
            val result = getTvShowReleasesUseCase(month, FIRST_PAGE)
                .toUiState { tvShows -> tvShows.toTvShowMediaList() }
            _state.update { it.copy(series = result) }
        }
    }
}
