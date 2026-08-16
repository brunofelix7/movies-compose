package dev.brunofelix.movies.feature.search.presentation.state

import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SearchState(
    val query: String = "",
    val searchResults: Flow<PagingData<Media>> = flowOf(PagingData.empty()),
    val isLoading: Boolean = false
)
