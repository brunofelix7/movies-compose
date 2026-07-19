package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth.assertThat
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.data.api.mapper.toDomain
import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.presentation.mapper.toUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.feature.detail.domain.use_case.DeleteMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.GetMovieDetailsUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.IsFavoriteMovieUseCase
import dev.brunofelix.movies.feature.detail.domain.use_case.SaveMovieUseCase
import dev.brunofelix.movies.feature.detail.presentation.viewmodel.MovieDetailViewModel
import dev.brunofelix.movies.test_util.MainDispatcherRule
import dev.brunofelix.movies.test_util.factory.MovieDtoFactory
import dev.brunofelix.movies.test_util.fake.FakeMovie
import dev.brunofelix.movies.test_util.getOrAwaitValueTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val getMovieDetailsUseCase = mockk<GetMovieDetailsUseCase>()
    val saveMovieUseCase = mockk<SaveMovieUseCase>()
    val isFavoriteMovieUseCase = mockk<IsFavoriteMovieUseCase>()
    val deleteMovieUseCase = mockk<DeleteMovieUseCase>()

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

        coEvery { getMovieDetailsUseCase(1) } returns Resource.Success(movie)
        coEvery { isFavoriteMovieUseCase(movie.id) } returns false

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
        coEvery { getMovieDetailsUseCase(1) } returns Resource.Error(Exception("Error"))

        // Act
        viewModel.getDetails(1)
        val result = viewModel.uiState.asLiveData().getOrAwaitValueTest()

        // Assert
        assertThat(result).isEqualTo(expectedState)
    }
}
