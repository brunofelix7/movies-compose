package dev.brunofelix.movies.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.brunofelix.movies.core.data.local.dao.MovieDao
import dev.brunofelix.movies.core.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    exportSchema = false,
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}