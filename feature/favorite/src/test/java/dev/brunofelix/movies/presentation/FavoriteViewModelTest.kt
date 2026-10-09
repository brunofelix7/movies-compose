package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.GetFavoriteMediasUseCase
import dev.brunofelix.movies.domain.use_case.SyncFavoriteWatchProvidersUseCase
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
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
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
    val syncFavoriteWatchProvidersUseCase = mockk<SyncFavoriteWatchProvidersUseCase>()

    val netflix = WatchProvider(id = 8L, name = "Netflix")
    val prime = WatchProvider(id = 119L, name = "Prime Video")
    val max = WatchProvider(id = 1899L, name = "Max")

    val movie = Media(id = 1L, title = "Dune", type = MediaType.MOVIE)
    val tvShow = Media(id = 2L, title = "Dark", type = MediaType.TV_SHOW)
    var favorites = MutableStateFlow(listOf(movie, tvShow))

    fun viewModel() = FavoriteViewModel(getFavoriteMediasUseCase, deleteMediaUseCase, syncFavoriteWatchProvidersUseCase)

    beforeSpec { Dispatchers.setMain(testDispatcher) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        clearAllMocks()
        favorites = MutableStateFlow(listOf(movie, tvShow))
        every { getFavoriteMediasUseCase() } returns favorites
        coEvery { syncFavoriteWatchProvidersUseCase() } just runs
    }

    describe("init") {
        it("should sync the streaming services of the favorites") {
            runTest(testDispatcher) {
                viewModel()

                coVerify(exactly = 1) { syncFavoriteWatchProvidersUseCase() }
            }
        }
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

    describe("streaming filter") {
        val dune = movie.copy(watchProviders = listOf(netflix, max))
        val alien = Media(id = 3L, title = "Alien", type = MediaType.MOVIE, watchProviders = listOf(prime, netflix))
        val heat = Media(id = 4L, title = "Heat", type = MediaType.MOVIE, watchProviders = emptyList())
        val dark = tvShow.copy(watchProviders = listOf(netflix))
        val streamingFavorites = listOf(dune, alien, heat, dark)

        it("should list the services of the category, the most common first and then by name") {
            runTest(testDispatcher) {
                favorites.value = streamingFavorites
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                viewModel.uiState.value.providers shouldBe listOf(netflix, max, prime)
                viewModel.uiState.value.selectedProviderId shouldBe null
            }
        }

        it("should show only the favorites offered by the selected service") {
            runTest(testDispatcher) {
                favorites.value = streamingFavorites
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                viewModel.onAction(FavoriteUiAction.OnProviderSelected(prime.id))

                viewModel.uiState.value.selectedProviderId shouldBe prime.id
                viewModel.uiState.value.medias shouldBe UiState.Success(listOf(alien.toUiModel()))
            }
        }

        it("should show every favorite again when the filter is cleared") {
            runTest(testDispatcher) {
                favorites.value = streamingFavorites
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }
                viewModel.onAction(FavoriteUiAction.OnProviderSelected(prime.id))

                viewModel.onAction(FavoriteUiAction.OnProviderSelected(null))

                viewModel.uiState.value.medias shouldBe
                    UiState.Success(listOf(dune, alien, heat).map { it.toUiModel() })
            }
        }

        it("should drop a selected service that offers nothing in the new category") {
            runTest(testDispatcher) {
                favorites.value = streamingFavorites
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }
                viewModel.onAction(FavoriteUiAction.OnProviderSelected(prime.id))

                viewModel.onAction(FavoriteUiAction.OnCategorySelected(FavoriteCategory.TV_SHOWS))

                viewModel.uiState.value shouldBe FavoriteUiState(
                    selectedCategory = FavoriteCategory.TV_SHOWS,
                    providers = listOf(netflix),
                    selectedProviderId = null,
                    medias = UiState.Success(listOf(dark.toUiModel()))
                )
            }
        }

        it("should keep a selected service that also offers favorites of the new category") {
            runTest(testDispatcher) {
                favorites.value = streamingFavorites
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }
                viewModel.onAction(FavoriteUiAction.OnProviderSelected(netflix.id))

                viewModel.onAction(FavoriteUiAction.OnCategorySelected(FavoriteCategory.TV_SHOWS))

                viewModel.uiState.value.selectedProviderId shouldBe netflix.id
            }
        }

        it("should hide the filter when no favorite of the category is on streaming") {
            runTest(testDispatcher) {
                favorites.value = listOf(heat, movie)
                val viewModel = viewModel()
                backgroundScope.launch { viewModel.uiState.collect {} }

                viewModel.uiState.value.providers shouldBe emptyList()
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
