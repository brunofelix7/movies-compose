package dev.brunofelix.movies.designsystem.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
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
class CustomButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderTextAndTriggerClickWhenOutlined() {
        var clicks = 0
        composeTestRule.setContent {
            PMovieTheme { CustomButton(text = "Watch", onClick = { clicks++ }, isOutlined = true) }
        }

        composeTestRule.onNodeWithText("Watch").assertIsDisplayed().performClick()

        clicks shouldBe 1
    }

    @Test
    fun shouldRenderTextAndTriggerClickWhenFilled() {
        var clicks = 0
        composeTestRule.setContent {
            PMovieTheme { CustomButton(text = "Save", onClick = { clicks++ }, isOutlined = false) }
        }

        composeTestRule.onNodeWithText("Save").assertIsDisplayed().performClick()

        clicks shouldBe 1
    }
}

@RunWith(AndroidJUnit4::class)
class SelectorChipTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderLabelAndTriggerClick() {
        var clicks = 0
        composeTestRule.setContent {
            PMovieTheme { SelectorChip(label = "Movies", isSelected = false, onClick = { clicks++ }) }
        }

        composeTestRule.onNodeWithText("Movies").assertIsDisplayed().performClick()

        clicks shouldBe 1
    }
}

@RunWith(AndroidJUnit4::class)
class PagingRetryTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderDefaultMessageAndTriggerRetry() {
        var retries = 0
        composeTestRule.setContent {
            PMovieTheme { PagingRetry(onRetry = { retries++ }) }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.error)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.retry)).performClick()

        retries shouldBe 1
    }

    @Test
    fun shouldRenderCustomMessage() {
        composeTestRule.setContent {
            PMovieTheme { PagingRetry(message = "Could not load more") }
        }

        composeTestRule.onNodeWithText("Could not load more").assertIsDisplayed()
    }
}
