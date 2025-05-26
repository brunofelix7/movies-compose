package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.feature.movie.domain.use_case.GetUpcomingMoviesUseCase
import dev.brunofelix.movies.feature.movie.presentation.state.MovieUpcomingUiState
import javax.inject.Inject

@HiltViewModel
class MovieUpcomingViewModel @Inject constructor(
    useCase: GetUpcomingMoviesUseCase
): ViewModel() {

    var uiState by mutableStateOf(MovieUpcomingUiState())
        private set

    init {
        uiState = uiState.copy(
            movies = useCase.invoke().cachedIn(viewModelScope)
        )
    }
}