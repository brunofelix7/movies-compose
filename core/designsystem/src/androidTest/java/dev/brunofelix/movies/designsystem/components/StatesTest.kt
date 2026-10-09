package dev.brunofelix.movies.designsystem.components

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
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

@RunWith(AndroidJUnit4::class)
class EmptyStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderDefaultMessage() {
        composeTestRule.setContent { PMovieTheme { EmptyState() } }

        composeTestRule.onNodeWithText(context.getString(R.string.no_results_found)).assertIsDisplayed()
    }

    @Test
    fun shouldRenderCustomMessage() {
        composeTestRule.setContent { PMovieTheme { EmptyState(message = "Nothing here") } }

        composeTestRule.onNodeWithText("Nothing here").assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class EmptyImageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheEmptyIcon() {
        composeTestRule.setContent { PMovieTheme { EmptyImage() } }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.empty_icon)).assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class LoadingStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderTheSpinner() {
        composeTestRule.setContent { PMovieTheme { LoadingState() } }

        composeTestRule.onNodeWithTag(LOADING_STATE_TEST_TAG).assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class DetailSkeletonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderTheSkeleton() {
        composeTestRule.setContent { PMovieTheme { DetailSkeleton() } }

        composeTestRule.onNodeWithTag(DETAIL_SKELETON_TEST_TAG).assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class DetailStatusLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderContentWithABackButton() {
        var backs = 0
        composeTestRule.setContent {
            PMovieTheme {
                DetailStatusLayout(onBackClick = { backs++ }) { Text("State content") }
            }
        }

        composeTestRule.onNodeWithText("State content").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.top_bar_back_icon)).performClick()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.top_bar_favorite_icon))
            .assertDoesNotExist()

        backs shouldBe 1
    }
}
