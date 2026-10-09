package dev.brunofelix.movies.presentation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.components.LOADING_STATE_TEST_TAG
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.presentation.model.MediaUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.feature.favorite.R
import dev.brunofelix.movies.presentation.components.FavoriteItem
import dev.brunofelix.movies.presentation.components.WatchProviderSelector
import dev.brunofelix.movies.presentation.model.FavoriteCategory
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.presentation.R as PresentationR

private val dune = MediaUiModel(
    id = 1L,
    title = "Dune",
    releaseDate = "22/10/2021",
    duration = "155min",
    voteAverage = "8.1",
    type = MediaType.MOVIE
)

private val netflix = WatchProvider(id = 8L, name = "Netflix")
private val prime = WatchProvider(id = 119L, name = "Prime Video")

@RunWith(AndroidJUnit4::class)
class FavoriteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheSpinnerWhenStateIsLoading() {
        composeTestRule.setContent {
            PMovieTheme { FavoriteScreen(uiState = FavoriteUiState(medias = UiState.Loading), onAction = {}) }
        }

        composeTestRule.onNodeWithTag(LOADING_STATE_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldRenderTheFavoritesWhenStateIsSuccess() {
        composeTestRule.setContent {
            PMovieTheme {
                FavoriteScreen(uiState = FavoriteUiState(medias = UiState.Success(listOf(dune))), onAction = {})
            }
        }

        composeTestRule.onNodeWithText(context.getString(PresentationR.string.movies)).assertIsDisplayed()
        composeTestRule.onNodeWithText("Dune").assertIsDisplayed()
    }

    @Test
    fun shouldRenderTheEmptyMessageWhenStateIsEmpty() {
        composeTestRule.setContent {
            PMovieTheme { FavoriteScreen(uiState = FavoriteUiState(medias = UiState.Empty), onAction = {}) }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.favorites_empty)).assertIsDisplayed()
    }

    @Test
    fun shouldRenderTheErrorWhenStateIsError() {
        composeTestRule.setContent {
            PMovieTheme {
                FavoriteScreen(
                    uiState = FavoriteUiState(medias = UiState.Error(UiText.DynamicString("Could not read"))),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Could not read").assertIsDisplayed()
    }

    @Test
    fun shouldTriggerCategoryAndMediaClickActions() {
        val actions = mutableListOf<FavoriteUiAction>()
        composeTestRule.setContent {
            PMovieTheme {
                FavoriteScreen(
                    uiState = FavoriteUiState(medias = UiState.Success(listOf(dune))),
                    onAction = { actions += it }
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(PresentationR.string.tv_shows)).performClick()
        composeTestRule.onNodeWithText("Dune").performClick()

        actions shouldBe listOf(
            FavoriteUiAction.OnCategorySelected(FavoriteCategory.TV_SHOWS),
            FavoriteUiAction.OnMediaClick(dune)
        )
    }

    @Test
    fun shouldHideTheStreamingFilterWithoutProviders() {
        composeTestRule.setContent {
            PMovieTheme {
                FavoriteScreen(uiState = FavoriteUiState(medias = UiState.Success(listOf(dune))), onAction = {})
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.favorites_all_providers)).assertDoesNotExist()
    }

    @Test
    fun shouldTriggerProviderActionsFromTheStreamingFilter() {
        val actions = mutableListOf<FavoriteUiAction>()
        composeTestRule.setContent {
            PMovieTheme {
                FavoriteScreen(
                    uiState = FavoriteUiState(
                        providers = listOf(netflix, prime),
                        selectedProviderId = netflix.id,
                        medias = UiState.Success(listOf(dune))
                    ),
                    onAction = { actions += it }
                )
            }
        }

        composeTestRule.onNodeWithText("Prime Video").performClick()
        composeTestRule.onNodeWithText("Netflix").performClick()
        composeTestRule.onNodeWithText(context.getString(R.string.favorites_all_providers)).performClick()

        actions shouldBe listOf(
            FavoriteUiAction.OnProviderSelected(prime.id),
            FavoriteUiAction.OnProviderSelected(null),
            FavoriteUiAction.OnProviderSelected(null)
        )
    }

    @Test
    fun shouldTriggerDeleteWhenSwipedToTheStart() {
        val actions = mutableListOf<FavoriteUiAction>()
        composeTestRule.setContent {
            PMovieTheme {
                FavoriteScreen(
                    uiState = FavoriteUiState(medias = UiState.Success(listOf(dune))),
                    onAction = { actions += it }
                )
            }
        }

        composeTestRule.onNodeWithText("Dune").performTouchInput { swipeLeft() }
        composeTestRule.waitForIdle()

        actions shouldBe listOf(FavoriteUiAction.OnDelete(dune))
    }
}

@RunWith(AndroidJUnit4::class)
class FavoriteItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderTheMovieDetailsAndTriggerClick() {
        var clicks = 0
        composeTestRule.setContent {
            PMovieTheme { FavoriteItem(media = dune, onClick = { clicks++ }) }
        }

        composeTestRule.onNodeWithText("22/10/2021").assertIsDisplayed()
        composeTestRule.onNodeWithText("155min").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dune").performClick()

        clicks shouldBe 1
    }

    @Test
    fun shouldHideTheDurationOfATvShow() {
        composeTestRule.setContent {
            PMovieTheme { FavoriteItem(media = dune.copy(type = MediaType.TV_SHOW)) }
        }

        composeTestRule.onNodeWithText("155min").assertDoesNotExist()
    }
}

@RunWith(AndroidJUnit4::class)
class WatchProviderSelectorTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheAllChipAndEveryProvider() {
        composeTestRule.setContent {
            PMovieTheme { WatchProviderSelector(providers = listOf(netflix, prime), selectedProviderId = null) }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.favorites_all_providers)).assertIsDisplayed()
        composeTestRule.onNodeWithText("Netflix").assertIsDisplayed()
        composeTestRule.onNodeWithText("Prime Video").assertIsDisplayed()
    }

    @Test
    fun shouldSelectAProviderAndClearItWhenTappedAgain() {
        val selections = mutableListOf<Long?>()
        composeTestRule.setContent {
            PMovieTheme {
                WatchProviderSelector(
                    providers = listOf(netflix, prime),
                    selectedProviderId = prime.id,
                    onProviderSelected = { selections += it }
                )
            }
        }

        composeTestRule.onNodeWithText("Netflix").performClick()
        composeTestRule.onNodeWithText("Prime Video").performClick()

        selections shouldBe listOf(netflix.id, null)
    }
}
