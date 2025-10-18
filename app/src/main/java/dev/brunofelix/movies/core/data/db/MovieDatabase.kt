package dev.brunofelix.movies.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.brunofelix.movies.core.data.db.dao.MovieDao
import dev.brunofelix.movies.core.data.db.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    exportSchema = false,
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}