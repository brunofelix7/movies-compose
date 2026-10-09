package dev.brunofelix.movies.presentation.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val PLAYER_TAG = "player"

@RunWith(AndroidJUnit4::class)
class YouTubePlayerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldRenderThePlayerView() {
        composeTestRule.setContent {
            PMovieTheme { YouTubePlayer(videoId = "Way9Dexny3w", modifier = Modifier.testTag(PLAYER_TAG)) }
        }

        composeTestRule.onNodeWithTag(PLAYER_TAG).assertIsDisplayed()
    }
}
