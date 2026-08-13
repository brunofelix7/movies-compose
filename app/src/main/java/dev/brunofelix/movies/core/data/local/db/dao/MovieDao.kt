package dev.brunofelix.movies.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.brunofelix.movies.core.data.local.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the movies table.
 * Contains all Room database operations for movie entities.
 */
@Dao
interface MovieDao {

    /**
     * Inserts or replaces a movie entity in the database.
     * @param entity The [MovieEntity] to be persisted.
     * @return The row ID of the inserted entity.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MovieEntity): Long

    /**
     * Deletes a specific movie entity from the database.
     * @param entity The [MovieEntity] to be removed.
     * @return The number of rows deleted.
     */
    @Delete
    suspend fun delete(entity: MovieEntity): Int

    /**
     * Finds a movie entity by its unique ID.
     * @param id The primary key ID of the movie.
     * @return The found [MovieEntity] or null if no match exists.
     */
    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getById(id: Long): MovieEntity?

    /**
     * Retrieves all movies from the database ordered alphabetically by title.
     * @return A [Flow] that emits a list of [MovieEntity] whenever the database changes.
     */
    @Query("SELECT * FROM movies ORDER BY title ASC")
    fun getAll(): Flow<List<MovieEntity>>
}
