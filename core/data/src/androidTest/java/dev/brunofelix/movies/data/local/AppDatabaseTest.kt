package dev.brunofelix.movies.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.data.local.entity.MediaEntity
import dev.brunofelix.movies.data.local.entity.WatchProviderEntity
import dev.brunofelix.movies.domain.model.enums.MediaType
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val MIGRATION_DATABASE_NAME = "migration_test_database"

/** The `medias` table exactly as Room created it in version 1. */
private const val CREATE_MEDIAS_V1 = "CREATE TABLE IF NOT EXISTS `medias` (`id` INTEGER NOT NULL, " +
    "`title` TEXT NOT NULL, `posterPath` TEXT NOT NULL, `voteAverage` REAL NOT NULL, " +
    "`duration` INTEGER NOT NULL, `releaseDate` TEXT NOT NULL, `type` TEXT NOT NULL, PRIMARY KEY(`id`))"

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    fun tearDown() {
        database.close()
        context.deleteDatabase(MIGRATION_DATABASE_NAME)
    }

    @Test
    fun shouldOpenTheDatabaseAtTheCurrentVersion() {
        database.openHelper.writableDatabase.version shouldBe 2
    }

    @Test
    fun shouldPersistTheMediaTypeThroughTheTypeConverter() = runTest {
        val entity = MediaEntity(
            id = 9L,
            title = "Dark",
            posterPath = "url",
            voteAverage = 8.7f,
            duration = 0,
            releaseDate = "2017-12-01",
            type = MediaType.TV_SHOW
        )

        database.mediaDao.insert(entity)

        database.mediaDao.getById(9L)?.type shouldBe MediaType.TV_SHOW
    }

    @Test
    fun shouldPersistTheWatchProvidersThroughTheTypeConverter() = runTest {
        val providers = listOf(WatchProviderEntity(id = 8L, name = "Netflix", logoPath = "logo"))
        val entity = MediaEntity(
            id = 9L,
            title = "Dark",
            posterPath = "url",
            voteAverage = 8.7f,
            duration = 0,
            releaseDate = "2017-12-01",
            type = MediaType.TV_SHOW,
            watchProviders = providers
        )

        database.mediaDao.insert(entity)

        database.mediaDao.getById(9L)?.watchProviders shouldBe providers
    }

    @Test
    fun shouldKeepTheFavoritesWhenMigratingFromVersion1() = runTest {
        createVersion1Database { db ->
            db.execSQL("INSERT INTO medias VALUES (1, 'Dune', 'url', 8.1, 155, '2021-10-22', 'MOVIE')")
        }

        val migrated = Room.databaseBuilder(context, AppDatabase::class.java, MIGRATION_DATABASE_NAME)
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()
        val media = migrated.mediaDao.getById(1L)
        migrated.close()

        media?.title shouldBe "Dune"
        media?.type shouldBe MediaType.MOVIE
        media?.watchProviders.shouldBeNull()
    }

    @Test
    fun shouldUseTheExpectedDatabaseName() {
        AppDatabase.DATABASE_NAME shouldBe "app_database"
    }

    private fun createVersion1Database(seed: (SQLiteDatabase) -> Unit) {
        val file = context.getDatabasePath(MIGRATION_DATABASE_NAME).apply { parentFile?.mkdirs() }
        SQLiteDatabase.openOrCreateDatabase(file, null).use { db ->
            db.execSQL(CREATE_MEDIAS_V1)
            seed(db)
            db.version = 1
        }
    }
}
