package dev.brunofelix.movies.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.brunofelix.movies.core.data.local.db.dao.MovieDao
import dev.brunofelix.movies.core.data.local.db.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}
