package dev.brunofelix.movies.feature.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.data.util.BasePagingSource
import dev.brunofelix.movies.core.data.util.extension.asPagerFlow
import dev.brunofelix.movies.feature.search.domain.use_case.SearchUseCase
import dev.brunofelix.movies.feature.search.presentation.state.SearchState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val PAGE_SIZE = 40
private val SEARCH_DEBOUNCE = 500.milliseconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    private val pagingConfig = PagingConfig(pageSize = PAGE_SIZE)

    fun onQueryChange(query: String) {
        searchJob?.cancel()
        _state.update { it.copy(query = query) }

        if (query.isBlank()) {
            clearResults()
            return
        }

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE)
            search(query)
        }
    }

    fun onSearch() {
        searchJob?.cancel()
        val query = _state.value.query
        if (query.isBlank()) clearResults() else search(query)
    }

    private fun search(query: String) {
        _state.update {
            it.copy(
                searchResults = pagingConfig.asPagerFlow {
                    BasePagingSource(pageSize = PAGE_SIZE) { page ->
                        searchUseCase(query, page)
                    }
                }.cachedIn(viewModelScope),
                isSearchTriggered = true
            )
        }
    }

    private fun clearResults() {
        _state.update {
            it.copy(
                searchResults = flowOf(PagingData.empty()),
                isSearchTriggered = false
            )
        }
    }
}
