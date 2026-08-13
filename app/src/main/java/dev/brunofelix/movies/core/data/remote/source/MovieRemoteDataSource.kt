package dev.brunofelix.movies.core.data.remote.source

import androidx.paging.PagingSource
import dev.brunofelix.movies.core.domain.model.Movie

/**
 * Remote data source for Movie-related operations.
 */
interface MovieRemoteDataSource {
    /**
     * Returns a [PagingSource] for popular movies.
     */
    fun getPopularPagingSource(): PagingSource<Int, Movie>

    /**
     * Returns a [PagingSource] for upcoming movies.
     */
    fun getUpcomingPagingSource(): PagingSource<Int, Movie>

    /**
     * Returns a [PagingSource] for top-rated movies.
     */
    fun getTopRatedPagingSource(): PagingSource<Int, Movie>

    /**
     * Fetches movie details by ID.
     * @param id The unique movie identifier.
     * @return A [Result] containing the [Movie] domain model.
     */
    suspend fun getDetails(id: Long): Result<Movie>
}
