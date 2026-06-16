package dev.brunofelix.movies.feature.popular.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.feature.popular.domain.use_case.GetPopularUseCase
import dev.brunofelix.movies.feature.popular.presentation.state.MoviePopularUiState
import javax.inject.Inject

@HiltViewModel
class MoviePopularViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularUseCase
): ViewModel() {

    var uiState by mutableStateOf(MoviePopularUiState())
        private set

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        uiState = uiState.copy(
            movies = getPopularUseCase().cachedIn(viewModelScope)
        )
    }
}