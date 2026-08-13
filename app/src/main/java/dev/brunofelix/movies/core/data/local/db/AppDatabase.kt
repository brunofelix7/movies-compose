package dev.brunofelix.movies.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.brunofelix.movies.core.data.local.db.dao.MovieDao
import dev.brunofelix.movies.core.data.local.db.entity.MovieEntity

/**
 * Main database class for the application using Room.
 * Defines the database configuration and serves as the main access point to the persisted data.
 */
@Database(
    entities = [MovieEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Provides access to [MovieDao] for movie-related database operations.
     */
    abstract val movieDao: MovieDao
}

