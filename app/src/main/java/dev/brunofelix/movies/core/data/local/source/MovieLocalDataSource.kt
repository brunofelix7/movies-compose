package dev.brunofelix.movies.core.data.local.source

import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the contract for local movie data operations.
 * It abstracts the data source implementation from the repository layer.
 */
interface MovieLocalDataSource {
    /**
     * Inserts a movie into the local database.
     * @param movie The [Movie] object to be inserted.
     * @return The row ID of the newly inserted movie.
     */
    suspend fun insert(movie: Movie): Long

    /**
     * Deletes a movie from the local database.
     * @param movie The [Movie] object to be deleted.
     * @return The number of rows affected by the deletion.
     */
    suspend fun delete(movie: Movie): Int

    /**
     * Retrieves a specific movie by its unique identifier.
     * @param id The unique ID of the movie.
     * @return The [Movie] if found, or null otherwise.
     */
    suspend fun getById(id: Long): Movie?

    /**
     * Retrieves all movies stored in the local database as a reactive stream.
     * @return A [Flow] emitting a list of all [Movie]s.
     */
    fun getAll(): Flow<List<Movie>>
}
