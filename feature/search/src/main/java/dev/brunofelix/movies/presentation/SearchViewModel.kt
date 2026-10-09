package dev.brunofelix.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.use_case.SearchMediaUseCase
import dev.brunofelix.movies.presentation.util.BasePagingSource
import dev.brunofelix.movies.presentation.util.extension.asPagerFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/**
 * Each page holds the movies and the TV shows of the same TMDB page, 20 of each, so a page with
 * fewer than 40 results is the last one.
 */
internal const val SEARCH_PAGE_SIZE = 40
internal val SEARCH_DEBOUNCE = 500.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMediaUseCase: SearchMediaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<SearchUiEvent>()
    val uiEvent: Flow<SearchUiEvent> = _uiEvent.receiveAsFlow()

    private val submittedQuery = MutableStateFlow<String?>(null)
    private var debounceJob: Job? = null

    val searchResults: Flow<PagingData<Media>> = submittedQuery
        .flatMapLatest { query ->
            if (query.isNullOrBlank()) {
                flowOf(PagingData.empty())
            } else {
                PagingConfig(pageSize = SEARCH_PAGE_SIZE).asPagerFlow {
                    BasePagingSource(pageSize = SEARCH_PAGE_SIZE) { page -> searchMediaUseCase(query, page) }
                }
            }
        }
        .cachedIn(viewModelScope)

    fun onAction(action: SearchUiAction) {
        when (action) {
            is SearchUiAction.OnQueryChange -> changeQuery(action.query)
            SearchUiAction.OnSearch -> searchNow()
            SearchUiAction.OnClose -> viewModelScope.launch { _uiEvent.send(SearchUiEvent.Close) }
            SearchUiAction.OnSessionEnd -> changeQuery("")
            is SearchUiAction.OnMediaClick -> viewModelScope.launch {
                _uiEvent.send(SearchUiEvent.NavigateToDetails(action.media))
            }
        }
    }

    private fun changeQuery(query: String) {
        debounceJob?.cancel()
        _uiState.update { it.copy(query = query) }

        if (query.isBlank()) {
            clearResults()
            return
        }

        debounceJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE)
            submit(query)
        }
    }

    private fun searchNow() {
        debounceJob?.cancel()
        val query = _uiState.value.query
        if (query.isBlank()) clearResults() else submit(query)
    }

    private fun submit(query: String) {
        submittedQuery.value = query
        _uiState.update { it.copy(isSearchTriggered = true) }
    }

    private fun clearResults() {
        submittedQuery.value = null
        _uiState.update { it.copy(isSearchTriggered = false) }
    }
}
