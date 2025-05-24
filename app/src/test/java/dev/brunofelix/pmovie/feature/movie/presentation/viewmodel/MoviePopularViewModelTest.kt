package dev.brunofelix.pmovie.feature.movie.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import dev.brunofelix.pmovie.core.util.exception.RemoteException
import dev.brunofelix.pmovie.feature.movie.domain.use_case.GetPopularMoviesUseCase
import dev.brunofelix.pmovie.test_util.MainDispatcherRule
import dev.brunofelix.pmovie.test_util.fake.FakePagingData
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
class MoviePopularViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var useCase: GetPopularMoviesUseCase

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