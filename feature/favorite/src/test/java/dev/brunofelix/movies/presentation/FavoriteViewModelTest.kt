package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.GetFavoriteMediasUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.LocalException
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.mapper.toUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.model.FavoriteCategory
import io.kotest.core.spec.style.DescribeSpec
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModelTest : DescribeSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getFavoriteMediasUseCase = mockk<GetFavoriteMediasUseCase>()
    val deleteMediaUseCase = mockk<DeleteMediaUseCase>()
    val movie = Media(id = 1L, title = "Dune", type = MediaType.MOVIE)
    val tvShow = Media(id = 2L, title = "Dark", type = MediaType.TV_SHOW)
    var favorites = MutableStateFlow(listOf(movie, tvShow))

    fun viewModel() = FavoriteViewModel(getFavoriteMediasUseCase, deleteMediaUseCase)

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        favorites = MutableStateFlow(listOf(movie, tvShow))
        every { getFavoriteMediasUseCase() } returns favorites
    }

    describe("uiState") {
        it("should show the favorite movies first") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                viewModel.uiState.value shouldBe FavoriteUiState(
                    selectedCategory = FavoriteCategory.MOVIES,
                    medias = UiState.Success(listOf(movie.toUiModel()))
                )
            }
        }

        it("should filter by the selected category") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                viewModel.onAction(FavoriteUiAction.OnCategorySelected(FavoriteCategory.TV_SHOWS))

                viewModel.uiState.value shouldBe FavoriteUiState(
                    selectedCategory = FavoriteCategory.TV_SHOWS,
                    medias = UiState.Success(listOf(tvShow.toUiModel()))
                )
            }
        }

        it("should be Empty when the category has no favorites") {
            runTest(testDispatcher) {
                favorites.value = listOf(tvShow)
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                viewModel.uiState.value.medias shouldBe UiState.Empty
            }
        }

        it("should follow the favorites as they change") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                favorites.value = listOf(tvShow)

                viewModel.uiState.value.medias shouldBe UiState.Empty
            }
        }

        it("should be Error when the favorites cannot be read") {
            runTest(testDispatcher) {
                every { getFavoriteMediasUseCase() } returns flow { throw LocalException.DatabaseError() }
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                val error = viewModel.uiState.value.medias
                error.shouldBeInstanceOf<UiState.Error>()
                error.uiText shouldBe UiText.StringResource(R.string.error_local_database)
            }
        }
    }

    describe("onAction") {
        it("should emit NavigateToDetails with the domain media on OnMediaClick") {
            runTest(testDispatcher) {
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }
                val events = mutableListOf<FavoriteUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(FavoriteUiAction.OnMediaClick(movie.toUiModel()))

                events shouldBe listOf(FavoriteUiEvent.NavigateToDetails(movie))
                eventJob.cancel()
            }
        }

        it("should delete the favorite on OnDelete") {
            runTest(testDispatcher) {
                coEvery { deleteMediaUseCase(movie) } returns Resource.Success(Unit)
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                viewModel.onAction(FavoriteUiAction.OnDelete(movie.toUiModel()))

                coVerify { deleteMediaUseCase(movie) }
            }
        }

        it("should show a toast when the delete fails") {
            runTest(testDispatcher) {
                coEvery { deleteMediaUseCase(movie) } returns Resource.Error(LocalException.DatabaseError())
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }
                val events = mutableListOf<FavoriteUiEvent>()
                val eventJob = backgroundScope.launch { viewModel.uiEvent.collect { events.add(it) } }

                viewModel.onAction(FavoriteUiAction.OnDelete(movie.toUiModel()))

                events shouldBe listOf(FavoriteUiEvent.ShowToast(UiText.StringResource(R.string.delete_media_error)))
                eventJob.cancel()
            }
        }
    }
})
