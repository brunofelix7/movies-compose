package dev.brunofelix.movies.feature.favorite.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.data.util.extension.toUiText
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.enums.FavoriteCategory
import dev.brunofelix.movies.core.domain.model.enums.MediaType
import dev.brunofelix.movies.core.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.core.presentation.mapper.toUiModel
import dev.brunofelix.movies.core.presentation.ui.model.MediaUiModel
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMediasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCase: GetFavoriteMediasUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(FavoriteCategory.MOVIES)
    val selectedCategory = _selectedCategory.asStateFlow()

    private var favoriteMedias: List<Media> = emptyList()

    val uiState: StateFlow<UiState<List<MediaUiModel>>> = combine(
        useCase().onEach { favoriteMedias = it },
        _selectedCategory
    ) { data, category ->
        val filteredData = data.filter {
            when (category) {
                FavoriteCategory.MOVIES -> it.type == MediaType.MOVIE
                FavoriteCategory.TV_SHOWS -> it.type == MediaType.TV_SHOW
            }
        }
        if (filteredData.isEmpty()) {
            UiState.Empty
        } else {
            UiState.Success(filteredData.map { it.toUiModel() })
        }
    }
        .onStart { emit(UiState.Loading) }
        .catch { emit(UiState.Error(it.toUiText())) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Initial
        )

    fun onCategorySelected(category: FavoriteCategory) {
        _selectedCategory.value = category
    }

    fun onDeleteFavorite(mediaUi: MediaUiModel) = viewModelScope.launch {
        favoriteMedias.find { it.id == mediaUi.id && it.type == mediaUi.type }?.let { media ->
            deleteMediaUseCase(media)
        }
    }
}