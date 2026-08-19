package dev.brunofelix.movies.core.domain.repository

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.util.Resource

/**
 * Repository interface for managing Movie data from both remote and local sources.
 */
interface MovieRepository {

    /**
     * Fetches detailed information for a specific movie from the remote source.
     * @param id The unique identifier of the movie.
     * @return A [Resource] containing the [Movie] details or an error.
     */
    suspend fun getDetails(id: Long): Resource<Movie>

    /**
     * Fetches a list of popular movies for a specific page.
     * @param page The page number to fetch.
     * @return A [Resource] containing a list of [Movie]s.
     */
    suspend fun getPopularMovies(page: Int): Resource<List<Movie>>

    /**
     * Fetches a list of upcoming movies for a specific page.
     * @param page The page number to fetch.
     * @return A [Resource] containing a list of [Movie]s.
     */
    suspend fun getUpcomingMovies(page: Int): Resource<List<Movie>>

    /**
     * Fetches a list of top-rated movies for a specific page.
     * @param page The page number to fetch.
     * @return A [Resource] containing a list of [Movie]s.
     */
    suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>>
}