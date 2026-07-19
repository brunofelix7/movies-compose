package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import dev.brunofelix.movies.core.util.exception.RemoteException
import dev.brunofelix.movies.feature.upcoming.domain.use_case.GetUpcomingUseCase
import dev.brunofelix.movies.feature.upcoming.presentation.viewmodel.MovieUpcomingViewModel
import dev.brunofelix.movies.test_util.MainDispatcherRule
import dev.brunofelix.movies.test_util.fake.FakePagingData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UpcomingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val useCase = mockk<GetUpcomingUseCase>()

    private val viewModel by lazy {
        MovieUpcomingViewModel(useCase)
    }

    @Test
    fun `when GetUpcomingMoviesUseCase get success, then return a PagingData of movies`() = runTest {
        // Given
        every { useCase() } returns flowOf(FakePagingData.fakeMovies)

        // When
        val result = viewModel.uiState.movies.first()

        // Then
        assertThat(result).isNotNull()
    }

    @Test(expected = Exception::class)
    fun `when GetPopularMoviesUseCase get error, then throw a RemoteException`() = runTest {
        // Given
        every { useCase() } throws RemoteException(0)

        // When
        val result = viewModel.uiState.movies.first()

        // Then
        assertThat(result).isNull()
    }
}