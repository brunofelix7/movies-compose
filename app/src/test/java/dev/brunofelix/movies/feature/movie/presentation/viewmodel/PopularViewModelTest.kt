package dev.brunofelix.movies.feature.movie.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import dev.brunofelix.movies.core.util.exception.RemoteException
import dev.brunofelix.movies.feature.popular.domain.use_case.GetPopularUseCase
import dev.brunofelix.movies.feature.popular.presentation.viewmodel.MoviePopularViewModel
import dev.brunofelix.movies.test_util.MainDispatcherRule
import dev.brunofelix.movies.test_util.fake.FakePagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PopularViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var useCase: GetPopularUseCase

    private val viewModel by lazy {
        MoviePopularViewModel(useCase)
    }

    @Test
    fun `test getPopularMoviesUseCase, when gets success, then returns movies PagingData`() = runTest {
        // Given
        `when`(useCase.invoke()).thenReturn(
            flowOf(FakePagingData.fakeMovies)
        )

        // When
        val result = viewModel.uiState.movies.first()

        // Then
        assertThat(result).isNotNull()
    }

    @Test(expected = Exception::class)
    fun `test getPopularMoviesUseCase, when gets error, then throws RemoteException`() = runTest {
        // Given
        `when`(useCase.invoke()).thenThrow(RemoteException(0))

        // When
        val result = viewModel.uiState.movies.first()

        // Then
        assertThat(result).isNull()
    }
}