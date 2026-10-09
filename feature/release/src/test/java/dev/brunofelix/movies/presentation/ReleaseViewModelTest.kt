package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.model.enums.ReleaseType
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieReleasesUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowReleasesUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.RemoteException
import dev.brunofelix.movies.domain.util.extension.toMedia
import dev.brunofelix.movies.domain.util.extension.toMovieMediaList
import dev.brunofelix.movies.domain.util.extension.toTvShowMediaList
import dev.brunofelix.movies.presentation.util.UiState
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
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
class ReleaseViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getLanguageUseCase = mockk<GetLanguageUseCase>()
    val getMovieReleasesUseCase = mockk<GetMovieReleasesUseCase>()
    val getTvShowReleasesUseCase = mockk<GetTvShowReleasesUseCase>()
    var language = MutableStateFlow(LanguageEnum.ENGLISH)
    val theaters = listOf(Movie(id = 1L))
    val streaming = listOf(Movie(id = 2L))
    val series = listOf(TvShow(id = 3L))

    fun viewModel() = ReleaseViewModel(getLanguageUseCase, getMovieReleasesUseCase, getTvShowReleasesUseCase)

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        language = MutableStateFlow(LanguageEnum.ENGLISH)
        every { getLanguageUseCase() } returns language
        coEvery { getMovieReleasesUseCase(any(), ReleaseType.THEATERS, 1) } returns Resource.Success(theaters)
        coEvery { getMovieReleasesUseCase(any(), ReleaseType.STREAMING, 1) } returns Resource.Success(streaming)
        coEvery { getTvShowReleasesUseCase(any(), 1) } returns Resource.Success(series)
    }

    describe("init") {
        it("should offer the current month and the next six") {
            runTest(testDispatcher) {
                val state = viewModel().uiState.value

                state.months shouldHaveSize 7
                state.months.first() shouldBe ReleaseMonth.current()
                state.selectedMonth shouldBe ReleaseMonth.current()
            }
        }

        it("should load the releases of the current month") {
            runTest(testDispatcher) {
                val state = viewModel().uiState.value

                state.theaters shouldBe UiState.Success(theaters.toMovieMediaList())
                state.streaming shouldBe UiState.Success(streaming.toMovieMediaList())
                state.series shouldBe UiState.Success(series.toTvShowMediaList())
                coVerify { getTvShowReleasesUseCase(ReleaseMonth.current(), 1) }
            }
        }

        it("should map a failed row to Error") {
            runTest(testDispatcher) {
                coEvery { getTvShowReleasesUseCase(any(), 1) } returns Resource.Error(RemoteException.NoInternet())

                viewModel().uiState.value.series.shouldBeInstanceOf<UiState.Error>()
            }
        }

        it("should reload when the language changes") {
            runTest(testDispatcher) {
                viewModel()

                language.value = LanguageEnum.SPANISH

                coVerify(exactly = 2) { getTvShowReleasesUseCase(any(), 1) }
            }
        }
    }

    describe("onAction") {
        it("should load the releases of the selected month") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val nextMonth = viewModel.uiState.value.months[1]

                viewModel.onAction(ReleaseUiAction.OnMonthSelected(nextMonth))

                viewModel.uiState.value.selectedMonth shouldBe nextMonth
                coVerify { getMovieReleasesUseCase(nextMonth, ReleaseType.THEATERS, 1) }
            }
        }

        it("should ignore the month that is already selected") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(ReleaseUiAction.OnMonthSelected(ReleaseMonth.current()))

                coVerify(exactly = 1) { getTvShowReleasesUseCase(any(), 1) }
            }
        }

        it("should reload the selected month on OnRetry") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(ReleaseUiAction.OnRetry)

                coVerify(exactly = 2) { getTvShowReleasesUseCase(ReleaseMonth.current(), 1) }
            }
        }

        it("should emit the navigation events with the selected month") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val events = mutableListOf<ReleaseUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }
                val media = theaters.first().toMedia()

                viewModel.onAction(ReleaseUiAction.OnMediaClick(media))
                viewModel.onAction(ReleaseUiAction.OnViewMoreClick(MediaListCategory.RELEASE_SERIES))

                events shouldBe listOf(
                    ReleaseUiEvent.NavigateToDetails(media),
                    ReleaseUiEvent.NavigateToMediaList(MediaListCategory.RELEASE_SERIES, ReleaseMonth.current().id)
                )
                eventJob.cancel()
            }
        }
    }
})
