package dev.brunofelix.movies.presentation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.LoadState
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.components.LOADING_STATE_TEST_TAG
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.extension.collectAsPreviewLazyPagingItems
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

@RunWith(AndroidJUnit4::class)
class MediaListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val uiState = MediaListUiState(category = MediaListCategory.MOVIE_TOP_RATED)
    private val dune = Media(id = 1L, title = "Dune")

    @Test
    fun shouldRenderTheTitleAndTheItems() {
        composeTestRule.setContent {
            PMovieTheme {
                MediaListScreen(uiState = uiState, medias = listOf(dune).collectAsPreviewLazyPagingItems(), onAction = {})
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.top_rated)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Dune").assertIsDisplayed()
    }

    @Test
    fun shouldRenderTheSpinnerWhileLoading() {
        composeTestRule.setContent {
            PMovieTheme {
                MediaListScreen(
                    uiState = uiState,
                    medias = emptyList<Media>().collectAsPreviewLazyPagingItems(refresh = LoadState.Loading),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(LOADING_STATE_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldRenderTheEmptyState() {
        composeTestRule.setContent {
            PMovieTheme {
                MediaListScreen(uiState = uiState, medias = emptyList<Media>().collectAsPreviewLazyPagingItems(), onAction = {})
            }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.no_results_found)).assertIsDisplayed()
    }

    @Test
    fun shouldTriggerMediaClickAndBack() {
        val actions = mutableListOf<MediaListUiAction>()
        composeTestRule.setContent {
            PMovieTheme {
                MediaListScreen(
                    uiState = uiState,
                    medias = listOf(dune).collectAsPreviewLazyPagingItems(),
                    onAction = { actions += it }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Dune").performClick()
        composeTestRule.onNodeWithContentDescription(context.getString(DesignSystemR.string.top_bar_back_icon)).performClick()

        actions shouldBe listOf(MediaListUiAction.OnMediaClick(dune), MediaListUiAction.OnBack)
    }
}
