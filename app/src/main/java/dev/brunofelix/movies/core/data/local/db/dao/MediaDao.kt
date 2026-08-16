package dev.brunofelix.movies.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.brunofelix.movies.core.data.local.db.entity.MediaEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing [MediaEntity] in the database.
 */
@Dao
interface MediaDao {

    /**
     * Inserts or replaces a media entity into the database.
     * @param entity The [MediaEntity] to be persisted.
     * @return The row ID of the inserted entity.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MediaEntity): Long

    /**
     * Deletes a media entity from the database.
     * @param entity The [MediaEntity] to be removed.
     * @return The number of rows deleted.
     */
    @Delete
    suspend fun delete(entity: MediaEntity): Int

    /**
     * Finds a media entity by its unique ID.
     * @param id The primary key ID of the media.
     * @return The found [MediaEntity] or null if no match exists.
     */
    @Query("SELECT * FROM medias WHERE id = :id")
    suspend fun getById(id: Long): MediaEntity?

    /**
     * Retrieves all medias from the database ordered alphabetically by title.
     * @return A [Flow] that emits a list of [MediaEntity] whenever the database changes.
     */
    @Query("SELECT * FROM medias ORDER BY title ASC")
    fun getAll(): Flow<List<MediaEntity>>
}