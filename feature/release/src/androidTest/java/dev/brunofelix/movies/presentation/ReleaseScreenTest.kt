package dev.brunofelix.movies.presentation

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
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.components.MonthSelector
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

private val months = ReleaseMonth.window(monthsBack = 0, monthsForward = 2)

@RunWith(AndroidJUnit4::class)
class ReleaseScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val dune = Media(id = 1L, title = "Dune")

    @Test
    fun shouldRenderTheMonthsAndTheRowsWhenStateIsSuccess() {
        composeTestRule.setContent {
            PMovieTheme {
                ReleaseScreen(
                    uiState = ReleaseUiState(months = months, theaters = UiState.Success(listOf(dune))),
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(months.first().label()).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.releases_theaters)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Dune").assertIsDisplayed()
    }

    @Test
    fun shouldTriggerMonthMediaAndViewMoreActions() {
        val actions = mutableListOf<ReleaseUiAction>()
        composeTestRule.setContent {
            PMovieTheme {
                ReleaseScreen(
                    uiState = ReleaseUiState(months = months, theaters = UiState.Success(listOf(dune))),
                    onAction = { actions += it }
                )
            }
        }

        composeTestRule.onNodeWithText(months[1].label()).performClick()
        composeTestRule.onNodeWithContentDescription("Dune").performClick()
        composeTestRule.onNodeWithText(context.getString(R.string.view_more)).performClick()

        actions shouldBe listOf(
            ReleaseUiAction.OnMonthSelected(months[1]),
            ReleaseUiAction.OnMediaClick(dune),
            ReleaseUiAction.OnViewMoreClick(MediaListCategory.RELEASE_THEATERS)
        )
    }

    @Test
    fun shouldTriggerRetryWhenStateIsError() {
        var capturedAction: ReleaseUiAction? = null
        val error = UiState.Error(UiText.DynamicString("Error"))
        composeTestRule.setContent {
            PMovieTheme {
                ReleaseScreen(
                    uiState = ReleaseUiState(months = months, theaters = error, streaming = error, series = error),
                    onAction = { capturedAction = it }
                )
            }
        }

        composeTestRule.onAllNodesWithText(context.getString(DesignSystemR.string.retry)).onFirst().performClick()

        capturedAction shouldBe ReleaseUiAction.OnRetry
    }

    @Test
    fun shouldRenderTheEmptyStateWhenStateIsEmpty() {
        composeTestRule.setContent {
            PMovieTheme {
                ReleaseScreen(uiState = ReleaseUiState(months = months, theaters = UiState.Empty), onAction = {})
            }
        }

        composeTestRule.onAllNodesWithText(context.getString(DesignSystemR.string.no_results_found))
            .onFirst()
            .assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class MonthSelectorTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldReportTheSelectedMonth() {
        var selected: ReleaseMonth? = null
        composeTestRule.setContent {
            PMovieTheme {
                MonthSelector(months = months, selectedMonth = months.first(), onMonthSelected = { selected = it })
            }
        }

        composeTestRule.onNodeWithText(months.last().label()).performClick()

        selected shouldBe months.last()
    }
}
