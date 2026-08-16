package dev.brunofelix.movies.feature.search.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Media
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing search data.
 */
interface SearchRepository {

    /**
     * Searches for media items based on the provided query.
     * @param query The search query.
     * @param pagingConfig Configuration for pagination behavior.
     * @return A [Flow] of [PagingData] containing the search results.
     */
    fun search(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Media>>
}
