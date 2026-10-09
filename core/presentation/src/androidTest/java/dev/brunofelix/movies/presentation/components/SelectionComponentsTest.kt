package dev.brunofelix.movies.presentation.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.model.Category
import dev.brunofelix.movies.presentation.navigation.Route
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private enum class TestCategory(override val titleResId: Int) : Category {
    MOVIES(R.string.movies),
    TV_SHOWS(R.string.tv_shows)
}

@RunWith(AndroidJUnit4::class)
class CategorySelectorTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderEveryCategoryAndReportTheSelectedOne() {
        var selected: Category? = null
        composeTestRule.setContent {
            PMovieTheme {
                CategorySelector(
                    categories = TestCategory.entries,
                    selectedCategory = TestCategory.MOVIES,
                    onCategorySelected = { selected = it }
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.movies)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.tv_shows)).performClick()

        selected shouldBe TestCategory.TV_SHOWS
    }
}

@RunWith(AndroidJUnit4::class)
class CustomNavBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldNavigateToAnotherTab() {
        var navigatedTo: Route? = null
        composeTestRule.setContent {
            PMovieTheme { CustomNavBar(currentTab = Route.Movies, onNavigate = { navigatedTo = it }) }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.releases)).performClick()

        navigatedTo shouldBe Route.Releases
    }

    @Test
    fun shouldIgnoreAClickOnTheCurrentTab() {
        var navigatedTo: Route? = null
        composeTestRule.setContent {
            PMovieTheme { CustomNavBar(currentTab = Route.Favorites, onNavigate = { navigatedTo = it }) }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.favorites)).performClick()

        navigatedTo shouldBe null
    }
}
