package dev.brunofelix.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.GetFavoriteMediasUseCase
import dev.brunofelix.movies.domain.use_case.SyncFavoriteWatchProvidersUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.mapper.toUiModel
import dev.brunofelix.movies.presentation.model.MediaUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.util.extension.toUiText
import dev.brunofelix.movies.presentation.model.FavoriteCategory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val STOP_TIMEOUT_MILLIS = 5_000L

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    getFavoriteMediasUseCase: GetFavoriteMediasUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase,
    syncFavoriteWatchProvidersUseCase: SyncFavoriteWatchProvidersUseCase
) : ViewModel() {

    private val selectedCategory = MutableStateFlow(FavoriteCategory.MOVIES)
    private val selectedProviderId = MutableStateFlow<Long?>(null)
    private var favoriteMedias: List<Media> = emptyList()

    val uiState: StateFlow<FavoriteUiState> = combine(
        getFavoriteMediasUseCase().onEach { favoriteMedias = it },
        selectedCategory,
        selectedProviderId
    ) { medias, category, providerId ->
        val inCategory = medias.filter { it.type == category.mediaType }
        val providers = inCategory.watchProvidersByPopularity()
        // A service that no longer offers any favorite of this category can't stay selected.
        val selectedId = providerId?.takeIf { id -> providers.any { it.id == id } }
        val filtered = if (selectedId == null) {
            inCategory
        } else {
            inCategory.filter { media -> media.watchProviders.orEmpty().any { it.id == selectedId } }
        }
        FavoriteUiState(
            selectedCategory = category,
            providers = providers,
            selectedProviderId = selectedId,
            medias = if (filtered.isEmpty()) UiState.Empty else UiState.Success(filtered.map { it.toUiModel() })
        )
    }
        .onStart { emit(FavoriteUiState(selectedCategory = selectedCategory.value, medias = UiState.Loading)) }
        .catch { emit(FavoriteUiState(selectedCategory = selectedCategory.value, medias = UiState.Error(it.toUiText()))) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = FavoriteUiState()
        )

    private val _uiEvent = Channel<FavoriteUiEvent>()
    val uiEvent: Flow<FavoriteUiEvent> = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch { syncFavoriteWatchProvidersUseCase() }
    }

    fun onAction(action: FavoriteUiAction) {
        when (action) {
            is FavoriteUiAction.OnCategorySelected -> selectedCategory.value = action.category
            is FavoriteUiAction.OnProviderSelected -> selectedProviderId.value = action.providerId
            is FavoriteUiAction.OnMediaClick -> findMedia(action.media)?.let { media ->
                viewModelScope.launch { _uiEvent.send(FavoriteUiEvent.NavigateToDetails(media)) }
            }
            is FavoriteUiAction.OnDelete -> delete(action.media)
        }
    }

    private fun delete(mediaUi: MediaUiModel) {
        val media = findMedia(mediaUi) ?: return
        viewModelScope.launch {
            if (deleteMediaUseCase(media) is Resource.Error) {
                _uiEvent.send(FavoriteUiEvent.ShowToast(UiText.StringResource(R.string.delete_media_error)))
            }
        }
    }

    private fun findMedia(mediaUi: MediaUiModel): Media? {
        return favoriteMedias.find { it.id == mediaUi.id && it.type == mediaUi.type }
    }
}

/**
 * Every streaming service offering at least one of these medias, the ones offering the most
 * first and then by name.
 */
private fun List<Media>.watchProvidersByPopularity(): List<WatchProvider> {
    return flatMap { it.watchProviders.orEmpty() }
        .groupBy { it.id }
        .values
        .sortedWith(compareByDescending<List<WatchProvider>> { it.size }.thenBy { it.first().name })
        .map { it.first() }
}
