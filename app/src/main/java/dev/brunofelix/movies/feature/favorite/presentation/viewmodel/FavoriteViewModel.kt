package dev.brunofelix.movies.feature.favorite.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchFavorites()
    }

    fun fetchFavorites() = viewModelScope.launch {
        useCase()
            .onStart {
                _uiState.value = UiState.Loading
            }
            .catch { e ->
                _uiState.value = UiState.Error(R.string.error)
            }
            .collectLatest { data ->
                _uiState.value = if (data.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(data)
                }
            }
    }
}