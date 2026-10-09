package dev.brunofelix.movies.presentation.detail

import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.model.Season
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.model.WatchAvailability
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.GetSeasonEpisodesUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowCastUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowDetailUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowVideosUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowWatchProvidersUseCase
import dev.brunofelix.movies.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.domain.use_case.UpdateFavoriteWatchProvidersUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.LocalException
import dev.brunofelix.movies.domain.util.exception.RemoteException
import dev.brunofelix.movies.domain.util.extension.toMedia
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.mapper.toUiModel
import dev.brunofelix.movies.presentation.model.TvShowUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
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
class TvShowDetailViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getTvShowDetailUseCase = mockk<GetTvShowDetailUseCase>()
    val getTvShowVideosUseCase = mockk<GetTvShowVideosUseCase>()
    val getTvShowCastUseCase = mockk<GetTvShowCastUseCase>()
    val getTvShowWatchProvidersUseCase = mockk<GetTvShowWatchProvidersUseCase>()
    val getSeasonEpisodesUseCase = mockk<GetSeasonEpisodesUseCase>()
    val saveMediaUseCase = mockk<SaveMediaUseCase>()
    val isFavoriteMediaUseCase = mockk<IsFavoriteMediaUseCase>()
    val deleteMediaUseCase = mockk<DeleteMediaUseCase>()
    val updateFavoriteWatchProvidersUseCase = mockk<UpdateFavoriteWatchProvidersUseCase>()
    val providers = listOf(WatchProvider(id = 8L, name = "Netflix"))

    val tvShow = TvShow(
        id = 3L,
        name = "Dark",
        seasons = listOf(
            Season(id = 1L, seasonNumber = 1, episodeCount = 10),
            Season(id = 2L, seasonNumber = 2, episodeCount = 8)
        )
    )
    val episodes = listOf(Episode(id = 11L, name = "Secrets", episodeNumber = 1))

    fun viewModel() = TvShowDetailViewModel(
        getTvShowDetailUseCase,
        getTvShowVideosUseCase,
        getTvShowCastUseCase,
        getTvShowWatchProvidersUseCase,
        getSeasonEpisodesUseCase,
        saveMediaUseCase,
        isFavoriteMediaUseCase,
        deleteMediaUseCase,
        updateFavoriteWatchProvidersUseCase
    )

    fun TvShowDetailViewModel.loadedTvShow(): TvShowUiModel = (uiState.value.tvShow as UiState.Success).data

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        coEvery { getTvShowDetailUseCase(3L) } returns Resource.Success(tvShow)
        coEvery { getTvShowVideosUseCase(3L) } returns Resource.Success(listOf(Video(key = "k", site = "YouTube")))
        coEvery { getTvShowCastUseCase(3L) } returns Resource.Success(emptyList())
        coEvery { getSeasonEpisodesUseCase(3L, any()) } returns Resource.Success(episodes)
        coEvery { getTvShowWatchProvidersUseCase(3L) } returns Resource.Success(providers)
        coEvery { isFavoriteMediaUseCase(3L) } returns Resource.Success(false)
        coEvery { updateFavoriteWatchProvidersUseCase(any(), any()) } returns Resource.Success(Unit)
    }

    describe("OnLoad") {
        it("should show the TV show and expand the first season") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))

                val state = viewModel.uiState.value
                state.tvShow shouldBe UiState.Success(
                    tvShow.toUiModel().copy(trailerKey = "k", watchAvailability = WatchAvailability.Streaming(providers))
                )
                state.seasons.expandedSeasonNumber shouldBe 1
                state.seasons.episodesOf(1) shouldBe UiState.Success(episodes.map { it.toUiModel() })
            }
        }

        it("should show the TV show as unavailable when no service offers it") {
            runTest(testDispatcher) {
                coEvery { getTvShowWatchProvidersUseCase(3L) } returns Resource.Success(emptyList())
                val viewModel = viewModel()

                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))

                viewModel.loadedTvShow().watchAvailability shouldBe WatchAvailability.Unavailable
            }
        }

        it("should hide the availability when the streaming services fail") {
            runTest(testDispatcher) {
                coEvery { getTvShowWatchProvidersUseCase(3L) } returns Resource.Error(RemoteException.Unknown())
                val viewModel = viewModel()

                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))

                viewModel.loadedTvShow().watchAvailability shouldBe null
            }
        }

        it("should refresh the stored streaming services of a favorite") {
            runTest(testDispatcher) {
                coEvery { isFavoriteMediaUseCase(3L) } returns Resource.Success(true)

                viewModel().onAction(TvShowDetailUiAction.OnLoad(3L))

                coVerify(exactly = 1) { updateFavoriteWatchProvidersUseCase(3L, providers) }
            }
        }

        it("should not store streaming services for a TV show that is not a favorite") {
            runTest(testDispatcher) {
                viewModel().onAction(TvShowDetailUiAction.OnLoad(3L))

                coVerify(exactly = 0) { updateFavoriteWatchProvidersUseCase(any(), any()) }
            }
        }

        it("should show an error when the details fail") {
            runTest(testDispatcher) {
                coEvery { getTvShowDetailUseCase(3L) } returns Resource.Error(RemoteException.NoInternet())
                val viewModel = viewModel()

                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))

                viewModel.uiState.value.tvShow shouldBe
                    UiState.Error(UiText.StringResource(R.string.error_network_no_internet))
            }
        }
    }

    describe("OnSeasonToggle") {
        it("should collapse the expanded season") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))

                viewModel.onAction(TvShowDetailUiAction.OnSeasonToggle(1))

                viewModel.uiState.value.seasons.expandedSeasonNumber shouldBe null
            }
        }

        it("should open another season and fetch its episodes once") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))

                viewModel.onAction(TvShowDetailUiAction.OnSeasonToggle(2))
                viewModel.onAction(TvShowDetailUiAction.OnSeasonToggle(1))
                viewModel.onAction(TvShowDetailUiAction.OnSeasonToggle(2))

                viewModel.uiState.value.seasons.expandedSeasonNumber shouldBe 2
                coVerify(exactly = 1) { getSeasonEpisodesUseCase(3L, 2) }
                coVerify(exactly = 1) { getSeasonEpisodesUseCase(3L, 1) }
            }
        }

        it("should keep the error of a season that failed and retry it") {
            runTest(testDispatcher) {
                coEvery { getSeasonEpisodesUseCase(3L, 2) } returns Resource.Error(RemoteException.Unknown())
                val viewModel = viewModel()
                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))
                viewModel.onAction(TvShowDetailUiAction.OnSeasonToggle(2))

                viewModel.uiState.value.seasons.episodesOf(2).shouldBeInstanceOf<UiState.Error>()

                coEvery { getSeasonEpisodesUseCase(3L, 2) } returns Resource.Success(emptyList())
                viewModel.onAction(TvShowDetailUiAction.OnSeasonRetry(2))

                viewModel.uiState.value.seasons.episodesOf(2) shouldBe UiState.Empty
            }
        }
    }

    describe("OnFavoriteToggle") {
        it("should save the TV show with its streaming services and refresh the favorite flag") {
            runTest(testDispatcher) {
                coEvery { saveMediaUseCase(any()) } returns Resource.Success(Unit)
                val viewModel = viewModel()
                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))
                coEvery { isFavoriteMediaUseCase(3L) } returns Resource.Success(true)

                viewModel.onAction(TvShowDetailUiAction.OnFavoriteToggle)

                coVerify { saveMediaUseCase(tvShow.toMedia().copy(watchProviders = providers)) }
                viewModel.uiState.value.isFavorite shouldBe true
            }
        }

        it("should show a toast when deleting fails") {
            runTest(testDispatcher) {
                coEvery { isFavoriteMediaUseCase(3L) } returns Resource.Success(true)
                coEvery { deleteMediaUseCase(any()) } returns Resource.Error(LocalException.DatabaseError())
                val viewModel = viewModel()
                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))
                val events = mutableListOf<TvShowDetailUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(TvShowDetailUiAction.OnFavoriteToggle)

                events shouldBe listOf(
                    TvShowDetailUiEvent.ShowToast(UiText.StringResource(R.string.delete_media_error))
                )
                eventJob.cancel()
            }
        }

        it("should show a toast when the favorite flag cannot be read") {
            runTest(testDispatcher) {
                coEvery { isFavoriteMediaUseCase(3L) } returns Resource.Error(LocalException.DatabaseError())
                val viewModel = viewModel()
                val events = mutableListOf<TvShowDetailUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(TvShowDetailUiAction.OnLoad(3L))

                events shouldBe listOf(
                    TvShowDetailUiEvent.ShowToast(UiText.StringResource(R.string.is_favorite_media_error))
                )
                eventJob.cancel()
            }
        }
    }

    describe("OnBack") {
        it("should emit NavigateBack") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val events = mutableListOf<TvShowDetailUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(TvShowDetailUiAction.OnBack)

                events shouldBe listOf(TvShowDetailUiEvent.NavigateBack)
                eventJob.cancel()
            }
        }
    }
})
