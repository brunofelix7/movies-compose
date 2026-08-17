package dev.brunofelix.movies.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.brunofelix.movies.core.data.local.db.converter.Converters
import dev.brunofelix.movies.core.data.local.db.dao.MediaDao
import dev.brunofelix.movies.core.data.local.db.entity.MediaEntity

/**
 * Main database class for the application using Room.
 * Defines the database configuration and serves as the main access point to the persisted data.
 */
@Database(
    entities = [MediaEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Provides access to [MediaDao] for movie-related database operations.
     */
    abstract val mediaDao: MediaDao
}

