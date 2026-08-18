package dev.brunofelix.movies.feature.favorite.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.data.util.extension.toUiText
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMediasUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    useCase: GetFavoriteMediasUseCase
) : ViewModel() {

    val uiState: StateFlow<UiState<List<Media>>> = useCase()
        .map { data ->
            if (data.isEmpty()) UiState.Empty else UiState.Success(data)
        }
        .onStart { emit(UiState.Loading) }
        .catch { UiState.Error(it.toUiText()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Initial
        )
}