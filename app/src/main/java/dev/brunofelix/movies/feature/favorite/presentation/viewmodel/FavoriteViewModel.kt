package dev.brunofelix.movies.feature.favorite.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.util.exception.LocalException
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoritesUseCase
import dev.brunofelix.movies.feature.favorite.presentation.viewmodel.state.FavoriteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        fetchFavorites()
    }

    fun fetchFavorites() = viewModelScope.launch {
        getFavoritesUseCase()
            .onStart {
                _uiState.value = FavoriteUiState.Loading
            }
            .catch { e ->
                _uiState.value = FavoriteUiState.Error(
                    messageRes = (e as? LocalException)?.messageRes ?: R.string.favorite_movies_error
                )
            }
            .collectLatest { movies ->
                _uiState.value = FavoriteUiState.Success(movies)
            }
    }
}