package dev.brunofelix.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.brunofelix.movies.data.local.entity.MediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MediaEntity): Long

    @Delete
    suspend fun delete(entity: MediaEntity): Int

    @Query("SELECT * FROM medias WHERE id = :id")
    suspend fun getById(id: Long): MediaEntity?

    @Query("SELECT * FROM medias ORDER BY title ASC")
    fun getAll(): Flow<List<MediaEntity>>
}
