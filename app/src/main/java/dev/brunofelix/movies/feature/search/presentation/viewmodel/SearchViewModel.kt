package dev.brunofelix.movies.feature.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query, isLoading = false) }
        searchJob?.cancel()
        
        if (query.isBlank()) {
            onSearch()
            return
        }

        searchJob = viewModelScope.launch {
            delay(500.milliseconds)
            _state.update { it.copy(isLoading = true) }
            onSearch()
        }
    }

    fun onSearch() {
        searchJob?.cancel()
        val currentQuery = _state.value.query
        if (currentQuery.isNotBlank()) {
            _state.update {
                it.copy(
                    searchResults = searchUseCase(currentQuery).cachedIn(viewModelScope),
                    isLoading = false
                )
            }
        } else {
            _state.update {
                it.copy(
                    searchResults = flowOf(PagingData.empty()),
                    isLoading = false
                )
            }
        }
    }
}
