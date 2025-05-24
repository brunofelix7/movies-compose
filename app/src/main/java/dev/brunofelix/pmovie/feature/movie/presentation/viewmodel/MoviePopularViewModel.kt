package dev.brunofelix.pmovie.feature.movie.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.pmovie.feature.movie.domain.use_case.GetPopularMoviesUseCase
import dev.brunofelix.pmovie.feature.movie.presentation.state.MoviePopularState
import javax.inject.Inject

@HiltViewModel
class MoviePopularViewModel @Inject constructor(
    useCase: GetPopularMoviesUseCase
): ViewModel() {

    var uiState by mutableStateOf(MoviePopularState())
        private set

    init {
        uiState = uiState.copy(
            movies = useCase.invoke().cachedIn(viewModelScope)
        )
    }
}