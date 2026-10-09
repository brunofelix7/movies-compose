package dev.brunofelix.movies.ui

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.brunofelix.movies.HiltTestActivity
import dev.brunofelix.movies.R
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.presentation.navigation.Route
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR
import dev.brunofelix.movies.core.presentation.R as PresentationR

@HiltAndroidTest
class MainScreenContentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun shouldShowTheBarsOnATabAndSwitchTabsByReplacing() {
        var replaced: Route? = null
        composeTestRule.setContent {
            PMovieTheme {
                MainScreenContent(
                    backStack = listOf(Route.Movies),
                    onNavigate = {},
                    onReplace = { replaced = it },
                    onBack = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.app_title)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(PresentationR.string.favorites)).performClick()

        replaced shouldBe Route.Favorites
    }

    @Test
    fun shouldOpenTheSettingsFromTheTopBar() {
        var navigatedTo: Route? = null
        composeTestRule.setContent {
            PMovieTheme {
                MainScreenContent(
                    backStack = listOf(Route.Movies),
                    onNavigate = { navigatedTo = it },
                    onReplace = {},
                    onBack = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(context.getString(DesignSystemR.string.top_bar_settings_icon))
            .performClick()

        navigatedTo shouldBe Route.Settings
    }

    @Test
    fun shouldShowTheSearchOverlayWhileSearching() {
        composeTestRule.setContent {
            PMovieTheme {
                MainScreenContent(
                    backStack = listOf(Route.Movies),
                    isSearchVisible = true,
                    onNavigate = {},
                    onReplace = {},
                    onBack = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.search_bar_hint)).assertIsDisplayed()
    }

    @Test
    fun shouldHideTheBarsOutsideTheTabs() {
        composeTestRule.setContent {
            PMovieTheme {
                MainScreenContent(
                    backStack = listOf(Route.Settings),
                    onNavigate = {},
                    onReplace = {},
                    onBack = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.app_title)).assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(PresentationR.string.favorites)).assertDoesNotExist()
    }
}
