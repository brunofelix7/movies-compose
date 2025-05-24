package dev.brunofelix.pmovie.feature.movie.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import dev.brunofelix.MainDispatcherRule
import dev.brunofelix.pmovie.core.util.exception.RemoteException
import dev.brunofelix.pmovie.feature.movie.fake.FakeMovieLocalDataSource
import dev.brunofelix.pmovie.feature.movie.fake.FakeMovieRemoteDataSource
import dev.brunofelix.pmovie.feature.movie.fake.FakeMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
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
    fun `when invoke() is called and find a movie by id, then returns movie`() = runTest {
        // Given
        remoteDataSource.setShouldReturnError(false)

        // When
        val result = useCase.invoke(1).single()

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `when invoke() is called and a movie is not found, then returns null`() = runTest {
        // Given
        remoteDataSource.setShouldReturnError(false)

        // When
        val result = useCase.invoke(-1).single()

        // Then
        assertThat(result).isNull()
    }

    @Test
    fun `when invoke() is called and get error, then throws RemoteException`() = runTest {
        // Given
        remoteDataSource.setShouldReturnError(true)

        // When - Nothing!

        // Then
        assertThrows(RemoteException::class.java) {
            runBlocking {
                useCase.invoke(1).single()
            }
        }
    }
}