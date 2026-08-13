package dev.brunofelix.movies.core.data.local.source

import dev.brunofelix.movies.core.data.local.db.dao.MovieDao
import dev.brunofelix.movies.core.data.local.db.mapper.toDomain
import dev.brunofelix.movies.core.data.local.db.mapper.toEntity
import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Interface that defines the contract for local movie data operations.
 * It abstracts the data source implementation from the repository layer.
 */
interface MovieLocalDataSource {
    suspend fun insert(movie: Movie): Long
    suspend fun delete(movie: Movie): Int
    suspend fun getById(id: Long): Movie?
    fun getAll(): Flow<List<Movie>>
}

/**
 * Implementation of [MovieLocalDataSource] using Room database as the persistence engine.
 * @property dao The [MovieDao] used to perform database operations.
 */
class MovieLocalDataSourceImpl @Inject constructor(
    private val dao: MovieDao
): MovieLocalDataSource {

    /**
     * Inserts a movie into the local database.
     * @param movie The [Movie] object to be inserted.
     * @return The row ID of the newly inserted movie.
     */
    override suspend fun insert(movie: Movie): Long {
        return dao.insert(movie.toEntity())
    }

    /**
     * Deletes a movie from the local database.
     * @param movie The [Movie] object to be deleted.
     * @return The number of rows affected by the deletion.
     */
    override suspend fun delete(movie: Movie): Int {
        return dao.delete(movie.toEntity())
    }

    /**
     * Retrieves a specific movie by its unique identifier.
     * @param id The unique ID of the movie.
     * @return The [Movie] if found, or null otherwise.
     */
    override suspend fun getById(id: Long): Movie? {
        return dao.getById(id)?.toDomain()
    }

    /**
     * Retrieves all movies stored in the local database as a reactive stream.
     * @return A [Flow] emitting a list of all [Movie]s.
     */
    override fun getAll(): Flow<List<Movie>> {
        return dao.getAll().map { entityList ->
            entityList.map { it.toDomain() }
        }
    }
}

