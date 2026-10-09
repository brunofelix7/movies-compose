package dev.brunofelix.movies.presentation.detail

import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieCastUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieDetailUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieVideosUseCase
import dev.brunofelix.movies.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.LocalException
import dev.brunofelix.movies.domain.util.exception.RemoteException
import dev.brunofelix.movies.domain.util.extension.toMedia
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.mapper.toUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getMovieDetailUseCase = mockk<GetMovieDetailUseCase>()
    val getMovieVideosUseCase = mockk<GetMovieVideosUseCase>()
    val getMovieCastUseCase = mockk<GetMovieCastUseCase>()
    val saveMediaUseCase = mockk<SaveMediaUseCase>()
    val isFavoriteMediaUseCase = mockk<IsFavoriteMediaUseCase>()
    val deleteMediaUseCase = mockk<DeleteMediaUseCase>()

    val movie = Movie(id = 7L, title = "Dune", duration = 155)
    val videos = listOf(Video(key = "trailer", site = "YouTube", type = "Trailer"))
    val cast = listOf(Cast(id = 1L, name = "Timothée Chalamet"))

    fun viewModel() = MovieDetailViewModel(
        getMovieDetailUseCase,
        getMovieVideosUseCase,
        getMovieCastUseCase,
        saveMediaUseCase,
        isFavoriteMediaUseCase,
        deleteMediaUseCase
    )

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        coEvery { getMovieDetailUseCase(7L) } returns Resource.Success(movie)
        coEvery { getMovieVideosUseCase(7L) } returns Resource.Success(videos)
        coEvery { getMovieCastUseCase(7L) } returns Resource.Success(cast)
        coEvery { isFavoriteMediaUseCase(7L) } returns Resource.Success(false)
    }

    describe("OnLoad") {
        it("should show the movie with its trailer and cast") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                viewModel.uiState.value shouldBe MovieDetailUiState(
                    movie = UiState.Success(
                        movie.toUiModel().copy(trailerKey = "trailer", cast = cast.map { it.toUiModel() })
                    ),
                    isFavorite = false
                )
            }
        }

        it("should still show the movie when the videos and the cast fail") {
            runTest(testDispatcher) {
                coEvery { getMovieVideosUseCase(7L) } returns Resource.Error(RemoteException.Unknown())
                coEvery { getMovieCastUseCase(7L) } returns Resource.Error(RemoteException.Unknown())
                val viewModel = viewModel()

                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                viewModel.uiState.value.movie shouldBe UiState.Success(movie.toUiModel())
            }
        }

        it("should show an error when the details fail") {
            runTest(testDispatcher) {
                coEvery { getMovieDetailUseCase(7L) } returns Resource.Error(RemoteException.NotFound())
                val viewModel = viewModel()

                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                viewModel.uiState.value.movie shouldBe
                    UiState.Error(UiText.StringResource(R.string.error_network_not_found))
            }
        }

        it("should not load the same movie twice") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))
                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                coVerify(exactly = 1) { getMovieDetailUseCase(7L) }
            }
        }

        it("should reflect a movie that is already a favorite") {
            runTest(testDispatcher) {
                coEvery { isFavoriteMediaUseCase(7L) } returns Resource.Success(true)
                val viewModel = viewModel()

                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                viewModel.uiState.value.isFavorite shouldBe true
            }
        }
    }

    describe("OnRetry") {
        it("should load the movie again after an error") {
            runTest(testDispatcher) {
                coEvery { getMovieDetailUseCase(7L) } returns Resource.Error(RemoteException.NoInternet())
                val viewModel = viewModel()
                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))
                coEvery { getMovieDetailUseCase(7L) } returns Resource.Success(movie)

                viewModel.onAction(MovieDetailUiAction.OnRetry)

                viewModel.uiState.value.movie shouldBe
                    UiState.Success(movie.toUiModel().copy(trailerKey = "trailer", cast = cast.map { it.toUiModel() }))
            }
        }
    }

    describe("OnFavoriteToggle") {
        it("should save a movie that is not a favorite") {
            runTest(testDispatcher) {
                coEvery { saveMediaUseCase(any()) } returns Resource.Success(Unit)
                val viewModel = viewModel()
                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))
                coEvery { isFavoriteMediaUseCase(7L) } returns Resource.Success(true)

                viewModel.onAction(MovieDetailUiAction.OnFavoriteToggle)

                coVerify { saveMediaUseCase(movie.toMedia()) }
                viewModel.uiState.value.isFavorite shouldBe true
            }
        }

        it("should delete a movie that is a favorite") {
            runTest(testDispatcher) {
                coEvery { isFavoriteMediaUseCase(7L) } returns Resource.Success(true)
                coEvery { deleteMediaUseCase(any()) } returns Resource.Success(Unit)
                val viewModel = viewModel()
                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))
                coEvery { isFavoriteMediaUseCase(7L) } returns Resource.Success(false)

                viewModel.onAction(MovieDetailUiAction.OnFavoriteToggle)

                coVerify { deleteMediaUseCase(movie.toMedia()) }
                viewModel.uiState.value.isFavorite shouldBe false
            }
        }

        it("should show a toast when saving fails") {
            runTest(testDispatcher) {
                coEvery { saveMediaUseCase(any()) } returns Resource.Error(LocalException.DatabaseError())
                val viewModel = viewModel()
                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))
                val events = mutableListOf<MovieDetailUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(MovieDetailUiAction.OnFavoriteToggle)

                events shouldBe listOf(
                    MovieDetailUiEvent.ShowToast(UiText.StringResource(R.string.mark_favorite_error))
                )
                eventJob.cancel()
            }
        }

        it("should do nothing before the movie is loaded") {
            runTest(testDispatcher) {
                viewModel().onAction(MovieDetailUiAction.OnFavoriteToggle)

                coVerify(exactly = 0) { saveMediaUseCase(any()) }
            }
        }
    }

    describe("OnBack") {
        it("should emit NavigateBack") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val events = mutableListOf<MovieDetailUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(MovieDetailUiAction.OnBack)

                events shouldBe listOf(MovieDetailUiEvent.NavigateBack)
                eventJob.cancel()
            }
        }
    }
})
