package dev.brunofelix.movies.designsystem.components

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.core.designsystem.R
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalMaterial3Api::class)
@RunWith(AndroidJUnit4::class)
class MainTopBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTitleAndTriggerSearchAndSettings() {
        var searches = 0
        var settings = 0
        composeTestRule.setContent {
            PMovieTheme {
                MainTopBar(title = "Movies Explorer", onSearch = { searches++ }, onSettings = { settings++ })
            }
        }

        composeTestRule.onNodeWithText("Movies Explorer").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.top_bar_search_icon)).performClick()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.top_bar_settings_icon)).performClick()

        searches shouldBe 1
        settings shouldBe 1
    }

    @Test
    fun shouldShowCancelActionWhileSearching() {
        var cancels = 0
        composeTestRule.setContent {
            PMovieTheme {
                MainTopBar(title = "Movies Explorer", isSearching = true, onCancelSearch = { cancels++ })
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.cancel)).assertIsDisplayed().performClick()

        cancels shouldBe 1
    }
}

@RunWith(AndroidJUnit4::class)
class SecondaryTopBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTitleAndTriggerBack() {
        var backs = 0
        composeTestRule.setContent {
            PMovieTheme { SecondaryTopBar(title = "Top Rated", onBack = { backs++ }) }
        }

        composeTestRule.onNodeWithText("Top Rated").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.top_bar_back_icon)).performClick()

        backs shouldBe 1
    }
}

@RunWith(AndroidJUnit4::class)
class DetailTopBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldTriggerBackAndFavoriteActions() {
        var backs = 0
        var favorites = 0
        composeTestRule.setContent {
            PMovieTheme {
                DetailTopBar(isFavorite = false, onBackClick = { backs++ }, onFavoriteClick = { favorites++ })
            }
        }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.top_bar_back_icon)).performClick()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.top_bar_favorite_icon)).performClick()

        backs shouldBe 1
        favorites shouldBe 1
    }

    @Test
    fun shouldHideFavoriteActionWhenNotAllowed() {
        composeTestRule.setContent {
            PMovieTheme { DetailTopBar(isFavorite = false, shouldShowFavorite = false) }
        }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.top_bar_favorite_icon))
            .assertDoesNotExist()
    }

    @Test
    fun shouldRenderTitleWhenScrolled() {
        composeTestRule.setContent {
            PMovieTheme { DetailTopBar(isFavorite = true, title = "Dune", scrollFraction = 1f) }
        }

        composeTestRule.onNodeWithText("Dune").assertIsDisplayed()
    }
}
