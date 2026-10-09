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
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

@RunWith(AndroidJUnit4::class)
class MovieHomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val dune = Media(id = 1L, title = "Dune")

    @Test
    fun shouldRenderTheRowsWhenStateIsSuccess() {
        composeTestRule.setContent {
            PMovieTheme {
                MovieHomeScreen(
                    uiState = MovieHomeUiState(
                        popular = UiState.Success(listOf(dune)),
                        upcoming = UiState.Empty,
                        topRated = UiState.Loading
                    ),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.popular)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.upcoming)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Dune").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.no_results_found)).assertIsDisplayed()
    }

    @Test
    fun shouldTriggerMediaClickAndViewMore() {
        val actions = mutableListOf<MovieHomeUiAction>()
        composeTestRule.setContent {
            PMovieTheme {
                MovieHomeScreen(
                    uiState = MovieHomeUiState(popular = UiState.Success(listOf(dune))),
                    onAction = { actions += it }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Dune").performClick()
        composeTestRule.onNodeWithText(context.getString(R.string.view_more)).performClick()

        actions shouldBe listOf(
            MovieHomeUiAction.OnMediaClick(dune),
            MovieHomeUiAction.OnViewMoreClick(MediaListCategory.MOVIE_POPULAR)
        )
    }

    @Test
    fun shouldTriggerRetryWhenStateIsError() {
        var capturedAction: MovieHomeUiAction? = null
        val error = UiState.Error(UiText.DynamicString("Error"))
        composeTestRule.setContent {
            PMovieTheme {
                MovieHomeScreen(
                    uiState = MovieHomeUiState(popular = error, upcoming = error, topRated = error),
                    onAction = { capturedAction = it }
                )
            }
        }

        composeTestRule.onAllNodesWithText(context.getString(DesignSystemR.string.retry)).onFirst().performClick()

        capturedAction shouldBe MovieHomeUiAction.OnRetry
    }
}
