package dev.brunofelix.movies.feature.movie.detail.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth.assertThat
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.domain.model.Video
import dev.brunofelix.movies.core.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.presentation.mapper.toUiModel
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.extension.toUiText
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieDetailUseCase
import dev.brunofelix.movies.feature.movie.detail.domain.use_case.GetMovieVideosUseCase
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

    private val getMovieDetailUseCase = mockk<GetMovieDetailUseCase>()
    private val getMovieVideosUseCase = mockk<GetMovieVideosUseCase>()
    private val saveMediaUseCase = mockk<SaveMediaUseCase>()
    private val isFavoriteMediaUseCase = mockk<IsFavoriteMediaUseCase>()
    private val deleteMediaUseCase = mockk<DeleteMediaUseCase>()

    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(
            getMovieDetailUseCase,
            getMovieVideosUseCase,
            saveMediaUseCase,
            isFavoriteMediaUseCase,
            deleteMediaUseCase
        )
    }

    @Test
    fun `when GetMovieDetailsUseCase get success, then returns 'Success' in uiState`() = runTest {
        // Arrange
        val movie = MovieDtoFactory().create(FakeMovie.JohnWick).toDomain()
        val movieUiState = movie.toUiModel().copy(trailerKey = "abc")
        val expectedState = UiState.Success(movieUiState)

        coEvery { getMovieDetailUseCase(1) } returns Resource.Success(movie)
        coEvery { getMovieVideosUseCase(1) } returns Resource.Success(listOf(
            Video(
                site = "YouTube",
                type = "Trailer",
                key = "abc"
            )
        ))
        coEvery { isFavoriteMediaUseCase(movie.id) } returns false

        // Act
        viewModel.getDetails(1)
        val result = viewModel.uiState.asLiveData().getOrAwaitValueTest()

        // Assert
        assertThat(result).isEqualTo(expectedState)
    }

    @Test
    fun `when GetMovieDetailsUseCase get error, then returns state 'Error' in uiState`() = runTest {
        // Arrange
        val exception = Exception("Error")
        val expectedState = UiState.Error(exception.toUiText())
        coEvery { getMovieDetailUseCase(1) } returns Resource.Error(exception)
        coEvery { getMovieVideosUseCase(1) } returns Resource.Success(emptyList())

        // Act
        viewModel.getDetails(1)
        val result = viewModel.uiState.asLiveData().getOrAwaitValueTest()

        // Assert
        assertThat(result).isEqualTo(expectedState)
    }
}
