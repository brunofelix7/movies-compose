package dev.brunofelix.movies.core.domain.repository

import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.util.Resource

/**
 * Repository interface for managing TV Show data from remote sources.
 */
interface TvShowRepository {
    /**
     * Fetches a list of popular TV shows for a specific page.
     * @param page The page number to fetch.
     * @return A [Resource] containing a list of [TvShow]s.
     */
    suspend fun getPopularTvShows(page: Int): Resource<List<TvShow>>

    /**
     * Fetches a list of top-rated TV shows for a specific page.
     * @param page The page number to fetch.
     * @return A [Resource] containing a list of [TvShow]s.
     */
    suspend fun getTopRatedTvShows(page: Int): Resource<List<TvShow>>

    /**
     * Fetches detailed information for a specific TV show from the remote source.
     * @param id The unique identifier of the TV show.
     * @return A [Resource] containing the [TvShow] details or an error.
     */
    suspend fun getDetails(id: Long): Resource<TvShow>
}