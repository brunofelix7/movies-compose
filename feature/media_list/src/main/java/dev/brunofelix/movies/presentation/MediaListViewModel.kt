package dev.brunofelix.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetMediaListUseCase
import dev.brunofelix.movies.presentation.util.BasePagingSource
import dev.brunofelix.movies.presentation.util.extension.asPagerFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** TMDB list endpoints return 20 items per page. */
internal const val MEDIA_LIST_PAGE_SIZE = 20

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MediaListViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getMediaListUseCase: GetMediaListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MediaListUiState())
    val uiState: StateFlow<MediaListUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<MediaListUiEvent>()
    val uiEvent: Flow<MediaListUiEvent> = _uiEvent.receiveAsFlow()

    /**
     * Rebuilt when the list changes or the preferred language changes, like the home pagers.
     */
    val medias: Flow<PagingData<Media>> = combine(
        _uiState.filter { it.category != null }.distinctUntilChanged(),
        getLanguageUseCase().distinctUntilChanged()
    ) { state, _ -> state }
        .flatMapLatest { state ->
            val category = checkNotNull(state.category)
            PagingConfig(pageSize = MEDIA_LIST_PAGE_SIZE).asPagerFlow {
                BasePagingSource(pageSize = MEDIA_LIST_PAGE_SIZE) { page ->
                    getMediaListUseCase(category, state.month, page)
                }
            }
        }
        .cachedIn(viewModelScope)

    fun onAction(action: MediaListUiAction) {
        when (action) {
            is MediaListUiAction.OnLoad -> _uiState.value = MediaListUiState(action.category, action.month)
            is MediaListUiAction.OnMediaClick -> sendEvent(MediaListUiEvent.NavigateToDetails(action.media))
            MediaListUiAction.OnBack -> sendEvent(MediaListUiEvent.NavigateBack)
        }
    }

    private fun sendEvent(event: MediaListUiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }
}
