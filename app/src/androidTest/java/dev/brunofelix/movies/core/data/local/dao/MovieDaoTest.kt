package dev.brunofelix.movies.core.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.brunofelix.movies.core.data.local.MovieDatabase
import dev.brunofelix.movies.test_util.factory.MovieEntityFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MovieDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var db: MovieDatabase

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun test_getAll_whenDatabaseIsEmpty_thenReturnsEmptyList() = runTest {
        val movieList = db.movieDao.getAll().first()
        assertThat(movieList).isEmpty()
    }

    @Test
    fun test_getAll_whenDatabaseHasMovieListInserted_thenReturnsMovieListOrderedByTitle() = runTest {
        // Arrange
        val movieList = MovieEntityFactory.createList()
        movieList.forEach { db.movieDao.insert(it) }

        // Act
        val movies = db.movieDao.getAll().first()

        // Assert
        assertThat(movies?.get(0)?.title).isEqualTo("Movie 1")
    }

    @Test
    fun test_getById_whenDatabaseIsEmpty_thenReturnsNull() = runTest {
        val movie = db.movieDao.getById(1)
        assertThat(movie).isNull()
    }

    @Test
    fun test_getById_whenDatabaseHasMovieInserted_thenReturnsMovie() = runTest {
        // Arrange
        db.movieDao.insert(MovieEntityFactory.create(id = 1))

        // Act
        val movie = db.movieDao.getById(1)

        // Assert
        assertThat(movie?.title).isEqualTo("Movie 1")
    }

    @Test
    fun test_insert_whenMovieIsValid_thenReturnsMovieId() = runTest {
        // Arrange
        val movie = MovieEntityFactory.create(id = 107)

        // Act
        val result = db.movieDao.insert(movie)

        // Assert
        assertThat(result).isEqualTo(107)
    }

    @Test
    fun test_insert_whenMovieAlreadyExists_thenReplaceTheMovie() = runTest {
        // Arrange
        val movieOne = MovieEntityFactory.create(id = 1)
        val movieTwo = MovieEntityFactory.create(id = 1)
        db.movieDao.insert(movieOne)
        db.movieDao.insert(movieTwo)

        // Act
        val movies = db.movieDao.getAll().first()

        // Assert
        assertThat(movies?.size).isEqualTo(1)
    }

    @Test
    fun test_delete_whenMovieExists_thenDeleteTheMovie() = runTest {
        // Arrange
        val movie = MovieEntityFactory.create(id = 1)
        db.movieDao.insert(movie)

        // Act
        val result = db.movieDao.delete(movie)

        // Assert
        assertThat(result).isGreaterThan(0)
    }

    @Test
    fun test_delete_whenMovieDoesNotExist_thenDoNotDeleteTheMovie() = runTest {
        // Arrange
        val movie = MovieEntityFactory.create(id = 1)

        // Act
        val result = db.movieDao.delete(movie)

        // Assert
        assertThat(result).isEqualTo(0)
    }
}