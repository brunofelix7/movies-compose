package dev.brunofelix.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.brunofelix.movies.data.local.converter.Converters
import dev.brunofelix.movies.data.local.dao.MediaDao
import dev.brunofelix.movies.data.local.entity.MediaEntity

@Database(
    entities = [MediaEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val mediaDao: MediaDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}
