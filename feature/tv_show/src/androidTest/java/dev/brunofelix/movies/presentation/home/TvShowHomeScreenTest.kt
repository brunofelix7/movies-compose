package dev.brunofelix.movies.presentation.home

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

@RunWith(AndroidJUnit4::class)
class TvShowHomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val dark = Media(id = 1L, title = "Dark", type = MediaType.TV_SHOW)

    @Test
    fun shouldRenderBothRowsWhenStateIsSuccess() {
        composeTestRule.setContent {
            PMovieTheme {
                TvShowHomeScreen(
                    uiState = TvShowHomeUiState(popular = UiState.Success(listOf(dark)), topRated = UiState.Empty),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.popular)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.top_rated)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Dark").assertIsDisplayed()
    }

    @Test
    fun shouldTriggerMediaClickAndViewMore() {
        val actions = mutableListOf<TvShowHomeUiAction>()
        composeTestRule.setContent {
            PMovieTheme {
                TvShowHomeScreen(
                    uiState = TvShowHomeUiState(popular = UiState.Success(listOf(dark))),
                    onAction = { actions += it }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Dark").performClick()
        composeTestRule.onNodeWithText(context.getString(R.string.view_more)).performClick()

        actions shouldBe listOf(
            TvShowHomeUiAction.OnMediaClick(dark),
            TvShowHomeUiAction.OnViewMoreClick(MediaListCategory.TV_SHOW_POPULAR)
        )
    }

    @Test
    fun shouldTriggerRetryWhenStateIsError() {
        var capturedAction: TvShowHomeUiAction? = null
        val error = UiState.Error(UiText.DynamicString("Error"))
        composeTestRule.setContent {
            PMovieTheme {
                TvShowHomeScreen(
                    uiState = TvShowHomeUiState(popular = error, topRated = error),
                    onAction = { capturedAction = it }
                )
            }
        }

        composeTestRule.onAllNodesWithText(context.getString(DesignSystemR.string.retry)).onFirst().performClick()

        capturedAction shouldBe TvShowHomeUiAction.OnRetry
    }
}
