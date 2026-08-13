package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.domain.model.TvShow

/**
 * Remote data source for TV Show-related operations.
 */
interface TvShowRemoteDataSource {
    /**
     * Returns a [PagingSource] for popular TV shows.
     */
    fun getPopularPagingSource(): PagingSource<Int, TvShow>

    /**
     * Returns a [PagingSource] for top-rated TV shows.
     */
    fun getTopRatedPagingSource(): PagingSource<Int, TvShow>

    /**
     * Fetches TV show details by ID.
     * @param id The unique TV show identifier.
     * @return A [Result] containing the [TvShow] domain model.
     */
    suspend fun getDetails(id: Long): Result<TvShow>
}
