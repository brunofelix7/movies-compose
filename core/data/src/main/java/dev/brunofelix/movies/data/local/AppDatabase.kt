package dev.brunofelix.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.brunofelix.movies.data.local.converter.Converters
import dev.brunofelix.movies.data.local.dao.MediaDao
import dev.brunofelix.movies.data.local.entity.MediaEntity

@Database(
    entities = [MediaEntity::class],
    exportSchema = false,
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val mediaDao: MediaDao

    companion object {
        const val DATABASE_NAME = "app_database"

        /** Adds the streaming services of each favorite, `NULL` until they are fetched. */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE medias ADD COLUMN watchProviders TEXT")
            }
        }
    }
}
