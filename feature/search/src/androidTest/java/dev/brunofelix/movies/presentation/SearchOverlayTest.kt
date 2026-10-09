package dev.brunofelix.movies.presentation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.paging.LoadState
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.components.LOADING_STATE_TEST_TAG
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.presentation.util.extension.collectAsPreviewLazyPagingItems
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

@RunWith(AndroidJUnit4::class)
class SearchOverlayTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val dune = Media(id = 1L, title = "Dune")

    @Test
    fun shouldShowTheSearchBarWhenVisible() {
        composeTestRule.setContent {
            PMovieTheme {
                SearchOverlay(
                    uiState = SearchUiState(),
                    searchResults = emptyList<Media>().collectAsPreviewLazyPagingItems(),
                    isVisible = true,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.search_bar_hint)).assertIsDisplayed()
    }

    @Test
    fun shouldRenderNothingWhenHidden() {
        composeTestRule.setContent {
            PMovieTheme {
                SearchOverlay(
                    uiState = SearchUiState(),
                    searchResults = emptyList<Media>().collectAsPreviewLazyPagingItems(),
                    isVisible = false,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(SEARCH_OVERLAY_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun shouldReportTheTypedQuery() {
        var capturedAction: SearchUiAction? = null
        composeTestRule.setContent {
            PMovieTheme {
                SearchOverlay(
                    uiState = SearchUiState(),
                    searchResults = emptyList<Media>().collectAsPreviewLazyPagingItems(),
                    isVisible = true,
                    onAction = { capturedAction = it }
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.search_bar_hint)).performTextInput("dune")

        capturedAction shouldBe SearchUiAction.OnQueryChange("dune")
    }

    @Test
    fun shouldShowTheSpinnerWhileSearching() {
        composeTestRule.setContent {
            PMovieTheme {
                SearchOverlay(
                    uiState = SearchUiState(query = "dune", isSearchTriggered = true),
                    searchResults = emptyList<Media>().collectAsPreviewLazyPagingItems(refresh = LoadState.Loading),
                    isVisible = true,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(LOADING_STATE_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldShowTheEmptyStateWithoutResults() {
        composeTestRule.setContent {
            PMovieTheme {
                SearchOverlay(
                    uiState = SearchUiState(query = "dune", isSearchTriggered = true),
                    searchResults = emptyList<Media>().collectAsPreviewLazyPagingItems(),
                    isVisible = true,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.no_results_found)).assertIsDisplayed()
    }

    @Test
    fun shouldTriggerMediaClickOnAResult() {
        var capturedAction: SearchUiAction? = null
        composeTestRule.setContent {
            PMovieTheme {
                SearchOverlay(
                    uiState = SearchUiState(query = "dune", isSearchTriggered = true),
                    searchResults = listOf(dune).collectAsPreviewLazyPagingItems(),
                    isVisible = true,
                    onAction = { capturedAction = it }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Dune").performClick()

        capturedAction shouldBe SearchUiAction.OnMediaClick(dune)
    }
}
