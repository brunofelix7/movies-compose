package dev.brunofelix.movies.feature.movie.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.domain.mapper.toMovieMediaList
import dev.brunofelix.movies.core.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.extension.toUiState
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetPopularUseCase
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetTopRatedUseCase
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetUpcomingUseCase
import dev.brunofelix.movies.feature.movie.home.presentation.state.MovieHomeState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FIRST_PAGE = 1

@HiltViewModel
class MovieHomeViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getPopularUseCase: GetPopularUseCase,
    private val getUpcomingUseCase: GetUpcomingUseCase,
    private val getTopRatedUseCase: GetTopRatedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieHomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getLanguageUseCase().distinctUntilChanged().collectLatest { load() }
        }
    }

    fun onRetry() {
        viewModelScope.launch { load() }
    }

    private suspend fun load() = coroutineScope {
        _state.value = MovieHomeState(
            popular = UiState.Loading,
            upcoming = UiState.Loading,
            topRated = UiState.Loading
        )
        launch {
            val result = getPopularUseCase(FIRST_PAGE).toUiState { it.toMovieMediaList() }
            _state.update { it.copy(popular = result) }
        }
        launch {
            val result = getUpcomingUseCase(FIRST_PAGE).toUiState { it.toMovieMediaList() }
            _state.update { it.copy(upcoming = result) }
        }
        launch {
            val result = getTopRatedUseCase(FIRST_PAGE).toUiState { it.toMovieMediaList() }
            _state.update { it.copy(topRated = result) }
        }
    }

    // Kept for the upcoming filter work, which will reuse the category selector.
    //
    // private val _selectedCategory = MutableStateFlow(MovieCategory.POPULAR)
    // val selectedCategory = _selectedCategory.asStateFlow()
    //
    // fun onCategorySelected(category: MovieCategory) {
    //     if (_selectedCategory.value == category) return
    //     _selectedCategory.value = category
    // }
}
