package dev.brunofelix.movies.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderTheSplash() {
        composeTestRule.setContent {
            PMovieTheme { SplashScreen(logoRes = android.R.drawable.star_big_on) }
        }

        composeTestRule.onNodeWithTag(SPLASH_SCREEN_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldFinishAfterTheHoldTime() {
        var finished = false
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.setContent {
            PMovieTheme { SplashRoute(logoRes = android.R.drawable.star_big_on, onFinished = { finished = true }) }
        }

        composeTestRule.mainClock.advanceTimeBy(SPLASH_HOLD_MILLIS - 1)
        finished shouldBe false

        composeTestRule.mainClock.advanceTimeBy(1)
        composeTestRule.waitForIdle()
        finished shouldBe true
    }
}
