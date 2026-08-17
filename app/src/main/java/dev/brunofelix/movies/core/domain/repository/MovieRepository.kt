package dev.brunofelix.movies.core.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

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
     * Provides a [Flow] of paginated popular movies.
     * @param pagingConfig Configuration for pagination behavior.
     */
    fun getPopularMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>>

    /**
     * Provides a [Flow] of paginated upcoming movies.
     * @param pagingConfig Configuration for pagination behavior.
     */
    fun getUpcomingMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>>

    /**
     * Provides a [Flow] of paginated top-rated movies.
     * @param pagingConfig Configuration for pagination behavior.
     */
    fun getTopRatedMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>>
}