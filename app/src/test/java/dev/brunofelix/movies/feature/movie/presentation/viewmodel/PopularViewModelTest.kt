package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import dev.brunofelix.movies.core.domain.util.exception.RemoteException
import dev.brunofelix.movies.feature.movie.popular.domain.use_case.GetPopularUseCase
import dev.brunofelix.movies.feature.movie.popular.presentation.viewmodel.MoviePopularViewModel
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
class PopularViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val useCase = mockk<GetPopularUseCase>()

    private val viewModel by lazy {
        MoviePopularViewModel(useCase)
    }

    @Test
    fun `test getPopularMoviesUseCase, when gets success, then returns movies PagingData`() = runTest {
        // Given
        every { useCase() } returns flowOf(FakePagingData.fakeMovies)

        // When
        val result = viewModel.uiState.movies.first()

        // Then
        assertThat(result).isNotNull()
    }

    @Test(expected = Exception::class)
    fun `test getPopularMoviesUseCase, when gets error, then throws RemoteException`() = runTest {
        // Given
        every { useCase() } throws RemoteException.Unknown()

        // When
        val result = viewModel.uiState.movies.first()

        // Then
        assertThat(result).isNull()
    }
}