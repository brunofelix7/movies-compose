package dev.brunofelix.movies.core.data.remote.source

import dev.brunofelix.movies.core.domain.model.Movie

/**
 * Remote data source for Movie-related operations.
 */
interface MovieRemoteDataSource {
    /**
     * Fetches a list of popular movies for a specific page.
     */
    suspend fun getPopulars(page: Int): Result<List<Movie>>

    /**
     * Fetches a list of upcoming movies for a specific page.
     */
    suspend fun getUpcoming(page: Int): Result<List<Movie>>

    /**
     * Fetches a list of top-rated movies for a specific page.
     */
    suspend fun getTopRated(page: Int): Result<List<Movie>>

    /**
     * Searches for movies by a query string.
     * @param query The search query.
     * @param page The page number to fetch.
     * @return A [Result] containing a list of [Movie]s.
     */
    suspend fun search(query: String, page: Int): Result<List<Movie>>

    /**
     * Fetches movie details by ID.
     * @param id The unique movie identifier.
     * @return A [Result] containing the [Movie] domain model.
     */
    suspend fun getDetails(id: Long): Result<Movie>
}
