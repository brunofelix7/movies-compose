package dev.brunofelix.movies.core.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing TV Show data from remote sources.
 */
interface TvShowRepository {
    /**
     * Provides a [Flow] of paginated popular TV shows.
     * @param pagingConfig Configuration for pagination behavior.
     */
    fun getPopularTvShows(pagingConfig: PagingConfig): Flow<PagingData<TvShow>>

    /**
     * Provides a [Flow] of paginated top-rated TV shows.
     * @param pagingConfig Configuration for pagination behavior.
     */
    fun getTopRatedTvShows(pagingConfig: PagingConfig): Flow<PagingData<TvShow>>

    /**
     * Fetches detailed information for a specific TV show from the remote source.
     * @param id The unique identifier of the TV show.
     * @return A [Resource] containing the [TvShow] details or an error.
     */
    suspend fun getDetails(id: Long): Resource<TvShow>

    /**
     * Searches for TV shows by a query string.
     * @param query The search query.
     * @param pagingConfig Configuration for pagination behavior.
     * @return A [Flow] of paginated search results.
     */
    fun search(query: String, pagingConfig: PagingConfig): Flow<PagingData<TvShow>>
}

