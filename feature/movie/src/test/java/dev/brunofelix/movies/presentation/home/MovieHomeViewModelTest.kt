package dev.brunofelix.movies.presentation.home

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetPopularMoviesUseCase
import dev.brunofelix.movies.domain.use_case.GetTopRatedMoviesUseCase
import dev.brunofelix.movies.domain.use_case.GetUpcomingMoviesUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.RemoteException
import dev.brunofelix.movies.domain.util.extension.toMedia
import dev.brunofelix.movies.domain.util.extension.toMovieMediaList
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class MovieHomeViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getLanguageUseCase = mockk<GetLanguageUseCase>()
    val getPopularMoviesUseCase = mockk<GetPopularMoviesUseCase>()
    val getUpcomingMoviesUseCase = mockk<GetUpcomingMoviesUseCase>()
    val getTopRatedMoviesUseCase = mockk<GetTopRatedMoviesUseCase>()
    var language = MutableStateFlow(LanguageEnum.ENGLISH)
    val movies = listOf(Movie(id = 1L, title = "Dune"), Movie(id = 2L, title = "Arrival"))

    fun viewModel() = MovieHomeViewModel(
        getLanguageUseCase,
        getPopularMoviesUseCase,
        getUpcomingMoviesUseCase,
        getTopRatedMoviesUseCase
    )

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        language = MutableStateFlow(LanguageEnum.ENGLISH)
        every { getLanguageUseCase() } returns language
        coEvery { getPopularMoviesUseCase(1) } returns Resource.Success(movies)
        coEvery { getUpcomingMoviesUseCase(1) } returns Resource.Success(movies)
        coEvery { getTopRatedMoviesUseCase(1) } returns Resource.Success(movies)
    }

    describe("init") {
        it("should load the three rows") {
            runTest(testDispatcher) {
                val expected = UiState.Success(movies.toMovieMediaList())

                viewModel().uiState.value shouldBe MovieHomeUiState(
                    popular = expected,
                    upcoming = expected,
                    topRated = expected
                )
            }
        }

        it("should map empty and failed rows") {
            runTest(testDispatcher) {
                coEvery { getUpcomingMoviesUseCase(1) } returns Resource.Success(emptyList())
                coEvery { getTopRatedMoviesUseCase(1) } returns Resource.Error(RemoteException.NoInternet())

                val state = viewModel().uiState.value

                state.upcoming shouldBe UiState.Empty
                state.topRated shouldBe UiState.Error(UiText.StringResource(R.string.error_network_no_internet))
            }
        }

        it("should reload when the language changes") {
            runTest(testDispatcher) {
                viewModel()

                language.value = LanguageEnum.SPANISH

                coVerify(exactly = 2) { getPopularMoviesUseCase(1) }
            }
        }
    }

    describe("onAction") {
        it("should reload every row on OnRetry") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(MovieHomeUiAction.OnRetry)

                coVerify(exactly = 2) { getUpcomingMoviesUseCase(1) }
            }
        }

        it("should emit NavigateToDetails on OnMediaClick") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val events = mutableListOf<MovieHomeUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }
                val media = movies.first().toMedia()

                viewModel.onAction(MovieHomeUiAction.OnMediaClick(media))

                events shouldBe listOf(MovieHomeUiEvent.NavigateToDetails(media))
                eventJob.cancel()
            }
        }

        it("should emit NavigateToMediaList on OnViewMoreClick") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val events = mutableListOf<MovieHomeUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(MovieHomeUiAction.OnViewMoreClick(MediaListCategory.MOVIE_UPCOMING))

                events shouldBe listOf(MovieHomeUiEvent.NavigateToMediaList(MediaListCategory.MOVIE_UPCOMING))
                eventJob.cancel()
            }
        }
    }
})
