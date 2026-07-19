package dev.brunofelix.movies.feature.movie.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.feature.detail.domain.use_case.GetMovieDetailsUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.GetMovieDetailsUseCaseImpl
import dev.brunofelix.movies.test_util.MainDispatcherRule
import dev.brunofelix.movies.test_util.fake.FakeMovieLocalDataSource
import dev.brunofelix.movies.test_util.fake.FakeMovieRemoteDataSource
import dev.brunofelix.movies.test_util.fake.FakeMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDetailsUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var remoteDataSource: FakeMovieRemoteDataSource
    private lateinit var localDataSource: FakeMovieLocalDataSource
    private lateinit var repository: FakeMovieRepository
    private lateinit var useCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        remoteDataSource = FakeMovieRemoteDataSource()
        localDataSource = FakeMovieLocalDataSource()
        repository = FakeMovieRepository(remoteDataSource, localDataSource)
        useCase = GetMovieDetailsUseCaseImpl(repository)
    }

    @Test
    fun `test useCase, when movie is found by id, then returns success with movie`() = runTest {
        // Given
        remoteDataSource.setShouldReturnError(false)

        // When
        val result = useCase.invoke(1)

        // Then
        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat((result as Resource.Success<Movie>).data.id).isEqualTo(1L)
    }

    @Test
    fun `test useCase, when movie is not found, then returns resource error`() = runTest {
        // Given
        remoteDataSource.setShouldReturnError(false)

        // When
        val result = useCase.invoke(-1)

        // Then
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `test useCase, when an error happens, then returns resource error`() = runTest {
        // Given
        remoteDataSource.setShouldReturnError(true)

        // When
        val result = useCase.invoke(1)

        // Then
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }
}