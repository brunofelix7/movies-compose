package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import dev.brunofelix.movies.feature.detail.presentation.viewmodel.MovieDetailsViewModel
import dev.brunofelix.movies.test_util.MainDispatcherRule
import dev.brunofelix.movies.test_util.factory.MovieDtoFactory
import dev.brunofelix.movies.test_util.fake.FakeGetMovieDetailsUseCase
import dev.brunofelix.movies.test_util.fake.FakeMovie
import dev.brunofelix.movies.test_util.fake.FakeMovieLocalDataSource
import dev.brunofelix.movies.test_util.fake.FakeMovieRemoteDataSource
import dev.brunofelix.movies.test_util.fake.FakeMovieRepository
import dev.brunofelix.movies.test_util.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var uiStateObserver: Observer<MovieDetailsUiState>

    private lateinit var remoteDataSource: FakeMovieRemoteDataSource
    private lateinit var localDataSource: FakeMovieLocalDataSource
    private lateinit var repository: FakeMovieRepository
    private lateinit var useCase: FakeGetMovieDetailsUseCase
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        remoteDataSource = FakeMovieRemoteDataSource()
        localDataSource = FakeMovieLocalDataSource()
        repository = FakeMovieRepository(remoteDataSource, localDataSource)
        useCase = FakeGetMovieDetailsUseCase(repository)
        viewModel = MovieDetailsViewModel(useCase)
        viewModel.uiState.observeForever(uiStateObserver)
    }

    @After
    fun tearDown() {
        viewModel.uiState.removeObserver(uiStateObserver)
    }

    @Test
    fun `when GetMovieDetailsUseCase get success, then returns 'Success' in uiState`() = runTest {
        // Arrange
        val movie = MovieDtoFactory().create(FakeMovie.JohnWick).toMovie()
        val uiState = MovieDetailsUiState.Success(movie)
        remoteDataSource.setShouldReturnError(false)

        // Act
        viewModel.getDetails(1)
        val result = viewModel.uiState.getOrAwaitValueTest()

        // Assert
        verify(uiStateObserver).onChanged(uiState)
        assertThat(result).isEqualTo(uiState)
    }

    @Test
    fun `when GetMovieDetailsUseCase get error, then returns state 'Error' in uiState`() = runTest {
        // Arrange
        val uiState = MovieDetailsUiState.Error(0)
        remoteDataSource.setShouldReturnError(true)

        // Act
        viewModel.getDetails(1)
        val result = viewModel.uiState.getOrAwaitValueTest()

        // Assert
        verify(uiStateObserver).onChanged(uiState)
        assertThat(result).isEqualTo(uiState)
    }
}