package dev.brunofelix.movies.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import dev.brunofelix.movies.data.local.AppDatabase
import dev.brunofelix.movies.data.local.entity.MediaEntity
import dev.brunofelix.movies.domain.model.enums.MediaType
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediaDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: MediaDao

    private fun entity(id: Long, title: String = "Movie $id") = MediaEntity(
        id = id,
        title = title,
        posterPath = "URL $id",
        voteAverage = 1F,
        duration = 1,
        releaseDate = "",
        type = MediaType.MOVIE
    )

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.mediaDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun shouldReturnEmptyListWhenDatabaseIsEmpty() = runTest {
        dao.getAll().first().shouldBeEmpty()
    }

    @Test
    fun shouldReturnMediasOrderedByTitle() = runTest {
        listOf(entity(2, "Movie 5"), entity(5, "Movie 3"), entity(1, "Movie 4"), entity(3, "Movie 1"))
            .forEach { dao.insert(it) }

        dao.getAll().first().map { it.title } shouldBe listOf("Movie 1", "Movie 3", "Movie 4", "Movie 5")
    }

    @Test
    fun shouldReturnNullWhenMediaIsNotStored() = runTest {
        dao.getById(1).shouldBeNull()
    }

    @Test
    fun shouldReturnStoredMediaById() = runTest {
        dao.insert(entity(1))

        dao.getById(1) shouldBe entity(1)
    }

    @Test
    fun shouldReturnInsertedRowId() = runTest {
        dao.insert(entity(107)) shouldBe 107L
    }

    @Test
    fun shouldReplaceMediaWithSameId() = runTest {
        dao.insert(entity(1, "Old title"))
        dao.insert(entity(1, "New title"))

        dao.getAll().first().map { it.title } shouldBe listOf("New title")
    }

    @Test
    fun shouldDeleteStoredMedia() = runTest {
        dao.insert(entity(1))

        dao.delete(entity(1)) shouldBe 1
        dao.getById(1).shouldBeNull()
    }

    @Test
    fun shouldNotDeleteWhenMediaIsNotStored() = runTest {
        dao.delete(entity(1)) shouldBe 0
    }

    @Test
    fun shouldEmitUpdatesWhenMediasChange() = runTest {
        dao.getAll().test {
            awaitItem().shouldBeEmpty()

            dao.insert(entity(1))
            awaitItem().map { it.id } shouldBe listOf(1L)

            dao.delete(entity(1))
            awaitItem().shouldBeEmpty()

            cancelAndIgnoreRemainingEvents()
        }
    }
}
