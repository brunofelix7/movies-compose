package dev.brunofelix.movies.feature.tv_show.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.domain.mapper.toTvShowMediaList
import dev.brunofelix.movies.core.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.extension.toUiState
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetPopularTvShowsUseCase
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetTopRatedTvShowsUseCase
import dev.brunofelix.movies.feature.tv_show.home.presentation.state.TvShowHomeState
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
class TvShowHomeViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getPopularUseCase: GetPopularTvShowsUseCase,
    private val getTopRatedUseCase: GetTopRatedTvShowsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TvShowHomeState())
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
        _state.value = TvShowHomeState(
            popular = UiState.Loading,
            topRated = UiState.Loading
        )
        launch {
            val result = getPopularUseCase(FIRST_PAGE).toUiState { it.toTvShowMediaList() }
            _state.update { it.copy(popular = result) }
        }
        launch {
            val result = getTopRatedUseCase(FIRST_PAGE).toUiState { it.toTvShowMediaList() }
            _state.update { it.copy(topRated = result) }
        }
    }

    // Kept for the upcoming filter work, which will reuse the category selector.
    //
    // private val _selectedCategory = MutableStateFlow(TvShowCategory.POPULAR)
    // val selectedCategory = _selectedCategory.asStateFlow()
    //
    // fun onCategorySelected(category: TvShowCategory) {
    //     if (_selectedCategory.value == category) return
    //     _selectedCategory.value = category
    // }
}
