package dev.brunofelix.movies.presentation

import androidx.paging.testing.asSnapshot
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetMediaListUseCase
import dev.brunofelix.movies.domain.util.Resource
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
class MediaListViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getLanguageUseCase = mockk<GetLanguageUseCase>()
    val getMediaListUseCase = mockk<GetMediaListUseCase>()
    var language = MutableStateFlow(LanguageEnum.ENGLISH)
    val medias = listOf(Media(id = 1L, title = "Dune"), Media(id = 2L, title = "Arrival"))
    val month = ReleaseMonth(2024, 5)

    fun viewModel() = MediaListViewModel(getLanguageUseCase, getMediaListUseCase)

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        language = MutableStateFlow(LanguageEnum.ENGLISH)
        every { getLanguageUseCase() } returns language
        coEvery { getMediaListUseCase(any(), any(), 1) } returns Resource.Success(medias)
    }

    describe("OnLoad") {
        it("should expose the list and page through it") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(MediaListUiAction.OnLoad(MediaListCategory.RELEASE_SERIES, month))

                viewModel.uiState.value shouldBe MediaListUiState(MediaListCategory.RELEASE_SERIES, month)
                viewModel.medias.asSnapshot() shouldBe medias
                coVerify { getMediaListUseCase(MediaListCategory.RELEASE_SERIES, month, 1) }
            }
        }
    }

    describe("onAction") {
        it("should emit NavigateToDetails and NavigateBack") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val events = mutableListOf<MediaListUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(MediaListUiAction.OnMediaClick(medias.first()))
                viewModel.onAction(MediaListUiAction.OnBack)

                events shouldBe listOf(MediaListUiEvent.NavigateToDetails(medias.first()), MediaListUiEvent.NavigateBack)
                eventJob.cancel()
            }
        }
    }
})
