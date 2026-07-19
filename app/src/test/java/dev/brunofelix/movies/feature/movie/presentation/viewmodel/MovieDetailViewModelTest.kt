package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth.assertThat
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.data.api.mapper.toDomain
import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.presentation.mapper.toUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.feature.detail.presentation.viewmodel.MovieDetailViewModel
import dev.brunofelix.movies.test_util.MainDispatcherRule
import dev.brunofelix.movies.test_util.factory.MovieDtoFactory
import dev.brunofelix.movies.test_util.fake.FakeMovie
import dev.brunofelix.movies.test_util.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    lateinit var saveMovieUseCase: SaveMovieUseCase

    @Mock
    lateinit var isFavoriteMovieUseCase: IsFavoriteMovieUseCase

    @Mock
    lateinit var deleteMovieUseCase: DeleteMovieUseCase

    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(
            getMovieDetailsUseCase,
            saveMovieUseCase,
            isFavoriteMovieUseCase,
            deleteMovieUseCase
        )
    }

    @Test
    fun `when GetMovieDetailsUseCase get success, then returns 'Success' in uiState`() = runTest {
        // Arrange
        val movie = MovieDtoFactory().create(FakeMovie.JohnWick).toDomain()
        val movieUiState = movie.toUiState()
        val expectedState = UiState.Success(movieUiState)

        `when`(getMovieDetailsUseCase(1)).thenReturn(Resource.Success(movie))
        `when`(isFavoriteMovieUseCase(movie.id)).thenReturn(false)

        // Act
        viewModel.getDetails(1)
        val result = viewModel.uiState.asLiveData().getOrAwaitValueTest()

        // Assert
        assertThat(result).isEqualTo(expectedState)
    }

    @Test
    fun `when GetMovieDetailsUseCase get error, then returns state 'Error' in uiState`() = runTest {
        // Arrange
        val expectedState = UiState.Error(R.string.movie_details_error)
        `when`(getMovieDetailsUseCase(1)).thenReturn(Resource.Error(Exception("Error")))

        // Act
        viewModel.getDetails(1)
        val result = viewModel.uiState.asLiveData().getOrAwaitValueTest()

        // Assert
        assertThat(result).isEqualTo(expectedState)
    }
}
