package dev.brunofelix.pmovie.feature.movie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import dev.brunofelix.MainDispatcherRule
import dev.brunofelix.pmovie.feature.movie.factory.MovieDtoFactory
import dev.brunofelix.pmovie.feature.movie.fake.FakeMovie
import dev.brunofelix.getOrAwaitValueTest
import dev.brunofelix.pmovie.feature.movie.fake.FakeMovieLocalDataSource
import dev.brunofelix.pmovie.feature.movie.fake.FakeMovieRemoteDataSource
import dev.brunofelix.pmovie.feature.movie.fake.FakeMovieRepository
import dev.brunofelix.pmovie.feature.movie.fake.FakeGetMovieDetailsUseCase
import dev.brunofelix.pmovie.feature.movie.presentation.state.MovieDetailsState
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
    lateinit var uiStateObserver: Observer<MovieDetailsState>

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
        // Given
        val movie = MovieDtoFactory().create(FakeMovie.JohnWick).toMovie()
        val uiState = MovieDetailsState.Success(movie)
        remoteDataSource.setShouldReturnError(false)

        // When
        viewModel.getDetails(1)
        val result = viewModel.uiState.getOrAwaitValueTest()

        // Then
        verify(uiStateObserver).onChanged(uiState)
        assertThat(result).isEqualTo(uiState)
    }

    @Test
    fun `when GetMovieDetailsUseCase get error, then returns state 'Error' in uiState`() = runTest {
        // Given
        val uiState = MovieDetailsState.Error()
        remoteDataSource.setShouldReturnError(true)

        // When
        viewModel.getDetails(1)
        val result = viewModel.uiState.getOrAwaitValueTest()

        // Then
        verify(uiStateObserver).onChanged(uiState)
        assertThat(result).isEqualTo(uiState)
    }
}