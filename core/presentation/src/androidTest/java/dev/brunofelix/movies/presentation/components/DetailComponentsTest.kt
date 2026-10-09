package dev.brunofelix.movies.presentation.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.model.CastUiModel
import dev.brunofelix.movies.presentation.util.UiText
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

@RunWith(AndroidJUnit4::class)
class CastSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheCastMembers() {
        composeTestRule.setContent {
            PMovieTheme {
                CastSection(cast = listOf(CastUiModel(id = 1L, name = "Pedro Pascal", character = "Joel Miller")))
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.cast_title)).assertIsDisplayed()
        composeTestRule.onNodeWithText("Pedro Pascal").assertIsDisplayed()
        composeTestRule.onNodeWithText("Joel Miller").assertIsDisplayed()
    }

    @Test
    fun shouldRenderNothingWhenTheCastIsEmpty() {
        composeTestRule.setContent {
            PMovieTheme { CastSection(cast = emptyList()) }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.cast_title)).assertDoesNotExist()
    }
}

@RunWith(AndroidJUnit4::class)
class MovieOverviewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheTitleAndTheOverview() {
        composeTestRule.setContent {
            PMovieTheme { MovieOverview(overview = "A desert planet.") }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.overview)).assertIsDisplayed()
        composeTestRule.onNodeWithText("A desert planet.").assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class MovieGenderContainerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderEveryGenre() {
        composeTestRule.setContent {
            PMovieTheme {
                MovieGenderContainer(gendersList = listOf(MovieGenre(name = "Action"), MovieGenre(name = "Drama")))
            }
        }

        composeTestRule.onNodeWithText("Action").assertIsDisplayed()
        composeTestRule.onNodeWithText("Drama").assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class ErrorLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheMessageAndTriggerRetry() {
        var retries = 0
        composeTestRule.setContent {
            PMovieTheme {
                ErrorLayout(errorMessage = UiText.DynamicString("No internet connection"), onRetry = { retries++ })
            }
        }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.error_icon)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.error_title)).assertIsDisplayed()
        composeTestRule.onNodeWithText("No internet connection").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.retry)).performClick()

        retries shouldBe 1
    }

    @Test
    fun shouldShowTheDefaultMessageWithoutRetry() {
        composeTestRule.setContent {
            PMovieTheme { ErrorLayout() }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.error_message)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.retry)).assertDoesNotExist()
    }
}
