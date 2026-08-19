package dev.brunofelix.movies.feature.search.domain.repository

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.util.Resource

/**
 * Repository interface for managing search data.
 */
interface SearchRepository {

    /**
     * Searches for media items based on the provided query.
     * @param query The search query.
     * @param page The page number to fetch.
     * @return A [Resource] containing a list of [Media]s.
     */
    suspend fun search(
        query: String,
        page: Int
    ): Resource<List<Media>>
}
