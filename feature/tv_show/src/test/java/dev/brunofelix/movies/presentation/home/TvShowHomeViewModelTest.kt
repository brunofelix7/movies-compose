package dev.brunofelix.movies.presentation.home

import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetPopularTvShowsUseCase
import dev.brunofelix.movies.domain.use_case.GetTopRatedTvShowsUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.RemoteException
import dev.brunofelix.movies.domain.util.extension.toMedia
import dev.brunofelix.movies.domain.util.extension.toTvShowMediaList
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
class TvShowHomeViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getLanguageUseCase = mockk<GetLanguageUseCase>()
    val getPopularTvShowsUseCase = mockk<GetPopularTvShowsUseCase>()
    val getTopRatedTvShowsUseCase = mockk<GetTopRatedTvShowsUseCase>()
    var language = MutableStateFlow(LanguageEnum.ENGLISH)
    val tvShows = listOf(TvShow(id = 1L, name = "Dark"))

    fun viewModel() = TvShowHomeViewModel(getLanguageUseCase, getPopularTvShowsUseCase, getTopRatedTvShowsUseCase)

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        language = MutableStateFlow(LanguageEnum.ENGLISH)
        every { getLanguageUseCase() } returns language
        coEvery { getPopularTvShowsUseCase(1) } returns Resource.Success(tvShows)
        coEvery { getTopRatedTvShowsUseCase(1) } returns Resource.Success(tvShows)
    }

    describe("init") {
        it("should load both rows") {
            runTest(testDispatcher) {
                val expected = UiState.Success(tvShows.toTvShowMediaList())

                viewModel().uiState.value shouldBe TvShowHomeUiState(popular = expected, topRated = expected)
            }
        }

        it("should map empty and failed rows") {
            runTest(testDispatcher) {
                coEvery { getPopularTvShowsUseCase(1) } returns Resource.Success(emptyList())
                coEvery { getTopRatedTvShowsUseCase(1) } returns Resource.Error(RemoteException.ServerError())

                val state = viewModel().uiState.value

                state.popular shouldBe UiState.Empty
                state.topRated shouldBe UiState.Error(UiText.StringResource(R.string.error_network_server))
            }
        }

        it("should reload when the language changes") {
            runTest(testDispatcher) {
                viewModel()

                language.value = LanguageEnum.PORTUGUESE

                coVerify(exactly = 2) { getTopRatedTvShowsUseCase(1) }
            }
        }
    }

    describe("onAction") {
        it("should reload both rows on OnRetry") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(TvShowHomeUiAction.OnRetry)

                coVerify(exactly = 2) { getPopularTvShowsUseCase(1) }
            }
        }

        it("should emit the navigation events") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val events = mutableListOf<TvShowHomeUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }
                val media = tvShows.first().toMedia()

                viewModel.onAction(TvShowHomeUiAction.OnMediaClick(media))
                viewModel.onAction(TvShowHomeUiAction.OnViewMoreClick(MediaListCategory.TV_SHOW_POPULAR))

                events shouldBe listOf(
                    TvShowHomeUiEvent.NavigateToDetails(media),
                    TvShowHomeUiEvent.NavigateToMediaList(MediaListCategory.TV_SHOW_POPULAR)
                )
                eventJob.cancel()
            }
        }
    }
})
