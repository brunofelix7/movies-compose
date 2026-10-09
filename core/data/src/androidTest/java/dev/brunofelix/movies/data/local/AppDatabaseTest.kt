package dev.brunofelix.movies.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.data.local.entity.MediaEntity
import dev.brunofelix.movies.domain.model.enums.MediaType
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun shouldOpenTheDatabaseAtTheCurrentVersion() {
        database.openHelper.writableDatabase.version shouldBe 1
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
    fun shouldUseTheExpectedDatabaseName() {
        AppDatabase.DATABASE_NAME shouldBe "app_database"
    }
}
