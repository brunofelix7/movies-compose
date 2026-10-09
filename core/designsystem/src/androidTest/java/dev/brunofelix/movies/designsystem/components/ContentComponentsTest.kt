package dev.brunofelix.movies.designsystem.components

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.core.designsystem.R
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SectionCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderTitleAndContent() {
        composeTestRule.setContent {
            PMovieTheme { SectionCard(title = "Overview") { Text("Card content") } }
        }

        composeTestRule.onNodeWithText("Overview").assertIsDisplayed()
        composeTestRule.onNodeWithText("Card content").assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class MovieInfoChipTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderText() {
        composeTestRule.setContent {
            PMovieTheme { MovieInfoChip(icon = Icons.Outlined.CalendarMonth, text = "01/04/2026") }
        }

        composeTestRule.onNodeWithText("01/04/2026").assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class GradientBackgroundTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderContent() {
        composeTestRule.setContent {
            PMovieTheme { GradientBackground { Text("Inside") } }
        }

        composeTestRule.onNodeWithText("Inside").assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class CustomSearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldShowPlaceholderWhenEmpty() {
        composeTestRule.setContent { PMovieTheme { CustomSearchBar(query = "") } }

        composeTestRule.onNodeWithText(context.getString(R.string.search_bar_hint)).assertIsDisplayed()
    }

    @Test
    fun shouldReportTypedText() {
        var query by mutableStateOf("")
        composeTestRule.setContent {
            PMovieTheme { CustomSearchBar(query = query, onQueryChange = { query = it }) }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.search_bar_hint)).performTextInput("dune")

        query shouldBe "dune"
    }

    @Test
    fun shouldClearTheQuery() {
        composeTestRule.setContent {
            var query by remember { mutableStateOf("dune") }
            PMovieTheme { CustomSearchBar(query = query, onQueryChange = { query = it }) }
        }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.search_bar_clear)).performClick()

        composeTestRule.onNodeWithText(context.getString(R.string.search_bar_hint)).assertIsDisplayed()
    }
}
