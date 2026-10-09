package dev.brunofelix.movies.presentation.detail

import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.model.WatchAvailability
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieCastUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieDetailUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieVideosUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieWatchProvidersUseCase
import dev.brunofelix.movies.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.domain.use_case.UpdateFavoriteWatchProvidersUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.LocalException
import dev.brunofelix.movies.domain.util.exception.RemoteException
import dev.brunofelix.movies.domain.util.extension.toMedia
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.mapper.toUiModel
import dev.brunofelix.movies.presentation.model.MovieUiModel
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
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getMovieDetailUseCase = mockk<GetMovieDetailUseCase>()
    val getMovieVideosUseCase = mockk<GetMovieVideosUseCase>()
    val getMovieCastUseCase = mockk<GetMovieCastUseCase>()
    val getMovieWatchProvidersUseCase = mockk<GetMovieWatchProvidersUseCase>()
    val saveMediaUseCase = mockk<SaveMediaUseCase>()
    val isFavoriteMediaUseCase = mockk<IsFavoriteMediaUseCase>()
    val deleteMediaUseCase = mockk<DeleteMediaUseCase>()
    val updateFavoriteWatchProvidersUseCase = mockk<UpdateFavoriteWatchProvidersUseCase>()

    val movie = Movie(id = 7L, title = "Dune", duration = 155)
    val videos = listOf(Video(key = "trailer", site = "YouTube", type = "Trailer"))
    val cast = listOf(Cast(id = 1L, name = "Timothée Chalamet"))
    val providers = listOf(WatchProvider(id = 8L, name = "Netflix"))

    fun viewModel() = MovieDetailViewModel(
        getMovieDetailUseCase,
        getMovieVideosUseCase,
        getMovieCastUseCase,
        getMovieWatchProvidersUseCase,
        saveMediaUseCase,
        isFavoriteMediaUseCase,
        deleteMediaUseCase,
        updateFavoriteWatchProvidersUseCase
    )

    fun MovieDetailViewModel.loadedMovie(): MovieUiModel = (uiState.value.movie as UiState.Success).data

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        coEvery { getMovieDetailUseCase(7L) } returns Resource.Success(movie)
        coEvery { getMovieVideosUseCase(7L) } returns Resource.Success(videos)
        coEvery { getMovieCastUseCase(7L) } returns Resource.Success(cast)
        coEvery { getMovieWatchProvidersUseCase(7L) } returns Resource.Success(providers)
        coEvery { isFavoriteMediaUseCase(7L) } returns Resource.Success(false)
        coEvery { updateFavoriteWatchProvidersUseCase(any(), any()) } returns Resource.Success(Unit)
    }

    describe("OnLoad") {
        it("should show the movie with its trailer, cast and streaming services") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                viewModel.uiState.value shouldBe MovieDetailUiState(
                    movie = UiState.Success(
                        movie.toUiModel().copy(
                            trailerKey = "trailer",
                            cast = cast.map { it.toUiModel() },
                            watchAvailability = WatchAvailability.Streaming(providers)
                        )
                    ),
                    isFavorite = false
                )
            }
        }

        it("should show the movie as in theaters when it premiered recently and no service offers it") {
            runTest(testDispatcher) {
                coEvery { getMovieDetailUseCase(7L) } returns
                    Resource.Success(movie.copy(releaseDate = LocalDate.now().toString()))
                coEvery { getMovieWatchProvidersUseCase(7L) } returns Resource.Success(emptyList())
                val viewModel = viewModel()

                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                viewModel.loadedMovie().watchAvailability shouldBe WatchAvailability.InTheaters
            }
        }

        it("should show the movie as unavailable when no service offers it") {
            runTest(testDispatcher) {
                coEvery { getMovieWatchProvidersUseCase(7L) } returns Resource.Success(emptyList())
                val viewModel = viewModel()

                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                viewModel.loadedMovie().watchAvailability shouldBe WatchAvailability.Unavailable
            }
        }

        it("should still show the movie when the videos, the cast and the streaming services fail") {
            runTest(testDispatcher) {
                coEvery { getMovieVideosUseCase(7L) } returns Resource.Error(RemoteException.Unknown())
                coEvery { getMovieCastUseCase(7L) } returns Resource.Error(RemoteException.Unknown())
                coEvery { getMovieWatchProvidersUseCase(7L) } returns Resource.Error(RemoteException.Unknown())
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

        it("should refresh the stored streaming services of a favorite") {
            runTest(testDispatcher) {
                coEvery { isFavoriteMediaUseCase(7L) } returns Resource.Success(true)
                val viewModel = viewModel()

                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                coVerify(exactly = 1) { updateFavoriteWatchProvidersUseCase(7L, providers) }
            }
        }

        it("should not store streaming services for a movie that is not a favorite") {
            runTest(testDispatcher) {
                viewModel().onAction(MovieDetailUiAction.OnLoad(7L))

                coVerify(exactly = 0) { updateFavoriteWatchProvidersUseCase(any(), any()) }
            }
        }

        it("should keep the stored streaming services of a favorite when they fail to load") {
            runTest(testDispatcher) {
                coEvery { isFavoriteMediaUseCase(7L) } returns Resource.Success(true)
                coEvery { getMovieWatchProvidersUseCase(7L) } returns Resource.Error(RemoteException.NoInternet())

                viewModel().onAction(MovieDetailUiAction.OnLoad(7L))

                coVerify(exactly = 0) { updateFavoriteWatchProvidersUseCase(any(), any()) }
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

                viewModel.uiState.value.movie shouldBe UiState.Success(
                    movie.toUiModel().copy(
                        trailerKey = "trailer",
                        cast = cast.map { it.toUiModel() },
                        watchAvailability = WatchAvailability.Streaming(providers)
                    )
                )
            }
        }
    }

    describe("OnFavoriteToggle") {
        it("should save a movie that is not a favorite together with its streaming services") {
            runTest(testDispatcher) {
                coEvery { saveMediaUseCase(any()) } returns Resource.Success(Unit)
                val viewModel = viewModel()
                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))
                coEvery { isFavoriteMediaUseCase(7L) } returns Resource.Success(true)

                viewModel.onAction(MovieDetailUiAction.OnFavoriteToggle)

                coVerify { saveMediaUseCase(movie.toMedia().copy(watchProviders = providers)) }
                viewModel.uiState.value.isFavorite shouldBe true
            }
        }

        it("should save the movie with unknown streaming services when they failed to load") {
            runTest(testDispatcher) {
                coEvery { getMovieWatchProvidersUseCase(7L) } returns Resource.Error(RemoteException.Unknown())
                coEvery { saveMediaUseCase(any()) } returns Resource.Success(Unit)
                val viewModel = viewModel()
                viewModel.onAction(MovieDetailUiAction.OnLoad(7L))

                viewModel.onAction(MovieDetailUiAction.OnFavoriteToggle)

                coVerify { saveMediaUseCase(movie.toMedia()) }
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

                coVerify { deleteMediaUseCase(movie.toMedia().copy(watchProviders = providers)) }
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
