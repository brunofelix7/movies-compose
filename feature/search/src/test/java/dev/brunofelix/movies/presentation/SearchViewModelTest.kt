package dev.brunofelix.movies.presentation

import androidx.paging.testing.asSnapshot
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.domain.use_case.SearchMediaUseCase
import dev.brunofelix.movies.domain.util.Resource
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
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val searchMediaUseCase = mockk<SearchMediaUseCase>()
    val results = listOf(
        Media(id = 1L, title = "Dune", type = MediaType.MOVIE),
        Media(id = 1L, title = "Dune", type = MediaType.TV_SHOW)
    )

    fun viewModel() = SearchViewModel(searchMediaUseCase)

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        coEvery { searchMediaUseCase(any(), 1) } returns Resource.Success(results)
    }

    describe("OnQueryChange") {
        it("should update the query right away and search after the debounce") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(SearchUiAction.OnQueryChange("dune"))

                viewModel.uiState.value shouldBe SearchUiState(query = "dune", isSearchTriggered = false)

                advanceTimeBy(SEARCH_DEBOUNCE.inWholeMilliseconds)
                runCurrent()

                viewModel.uiState.value.isSearchTriggered shouldBe true
                viewModel.searchResults.asSnapshot() shouldBe results
                coVerify { searchMediaUseCase("dune", 1) }
            }
        }

        it("should only search for the last query typed during the debounce") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(SearchUiAction.OnQueryChange("du"))
                viewModel.onAction(SearchUiAction.OnQueryChange("dune"))
                advanceTimeBy(SEARCH_DEBOUNCE.inWholeMilliseconds)
                runCurrent()
                viewModel.searchResults.asSnapshot()

                coVerify(exactly = 0) { searchMediaUseCase("du", any()) }
                coVerify { searchMediaUseCase("dune", 1) }
            }
        }

        it("should clear the results when the query is blank") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                viewModel.onAction(SearchUiAction.OnQueryChange("dune"))
                viewModel.onAction(SearchUiAction.OnSearch)

                viewModel.onAction(SearchUiAction.OnQueryChange(" "))
                advanceTimeBy(SEARCH_DEBOUNCE.inWholeMilliseconds)
                runCurrent()

                viewModel.uiState.value shouldBe SearchUiState(query = " ", isSearchTriggered = false)
                coVerify(exactly = 0) { searchMediaUseCase(" ", any()) }
            }
        }
    }

    describe("OnSearch") {
        it("should search right away") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                viewModel.onAction(SearchUiAction.OnQueryChange("dune"))

                viewModel.onAction(SearchUiAction.OnSearch)

                viewModel.uiState.value.isSearchTriggered shouldBe true
                viewModel.searchResults.asSnapshot() shouldBe results
            }
        }

        it("should not search a blank query") {
            runTest(testDispatcher) {
                val viewModel = viewModel()

                viewModel.onAction(SearchUiAction.OnSearch)

                viewModel.uiState.value.isSearchTriggered shouldBe false
                coVerify(exactly = 0) { searchMediaUseCase(any(), any()) }
            }
        }
    }

    describe("OnSessionEnd") {
        it("should drop the query and the results") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                viewModel.onAction(SearchUiAction.OnQueryChange("dune"))
                viewModel.onAction(SearchUiAction.OnSearch)

                viewModel.onAction(SearchUiAction.OnSessionEnd)

                viewModel.uiState.value shouldBe SearchUiState()
            }
        }
    }

    describe("events") {
        it("should emit Close and NavigateToDetails") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                val events = mutableListOf<SearchUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(SearchUiAction.OnMediaClick(results.first()))
                viewModel.onAction(SearchUiAction.OnClose)

                events shouldBe listOf(SearchUiEvent.NavigateToDetails(results.first()), SearchUiEvent.Close)
                eventJob.cancel()
            }
        }
    }
})
