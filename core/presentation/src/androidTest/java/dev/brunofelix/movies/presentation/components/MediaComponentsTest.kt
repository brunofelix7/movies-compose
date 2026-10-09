package dev.brunofelix.movies.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.util.extension.collectAsPreviewLazyPagingItems
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

private val medias = listOf(
    Media(id = 1L, title = "Dune"),
    Media(id = 2L, title = "Arrival")
)

@RunWith(AndroidJUnit4::class)
class MediaCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldExposeTheTitleAndReportTheClickedId() {
        var clickedId: Long? = null
        composeTestRule.setContent {
            PMovieTheme { MediaCard(media = medias.first(), onClick = { clickedId = it }) }
        }

        composeTestRule.onNodeWithContentDescription("Dune").performClick()

        clickedId shouldBe 1L
    }
}

@RunWith(AndroidJUnit4::class)
class MediaSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTitleItemsAndViewMore() {
        var clicked: Media? = null
        var viewMoreClicks = 0
        composeTestRule.setContent {
            PMovieTheme {
                MediaSection(
                    title = "Popular",
                    state = UiState.Success(medias),
                    onItemClick = { clicked = it },
                    onViewMore = { viewMoreClicks++ }
                )
            }
        }

        composeTestRule.onNodeWithText("Popular").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Arrival").performClick()
        composeTestRule.onNodeWithText(context.getString(R.string.view_more)).performClick()

        clicked shouldBe medias[1]
        viewMoreClicks shouldBe 1
    }

    @Test
    fun shouldOfferRetryWhenTheStateIsError() {
        var retries = 0
        composeTestRule.setContent {
            PMovieTheme {
                MediaSection(
                    title = "Popular",
                    state = UiState.Error(UiText.DynamicString("Error")),
                    onRetry = { retries++ }
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.retry)).performClick()

        retries shouldBe 1
    }

    @Test
    fun shouldShowTheEmptyMessageWhenTheStateIsEmpty() {
        composeTestRule.setContent {
            PMovieTheme { MediaSection(title = "Popular", state = UiState.Empty) }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.no_results_found)).assertIsDisplayed()
    }
}

@RunWith(AndroidJUnit4::class)
class MainContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderThePagedItemsAndReportTheClickedOne() {
        var clicked: Media? = null
        composeTestRule.setContent {
            PMovieTheme {
                MainContent(
                    paging = medias.collectAsPreviewLazyPagingItems(),
                    paddingValues = PaddingValues(),
                    onClick = { clicked = it },
                    media = { it }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Dune").performClick()

        clicked shouldBe medias.first()
    }

    @Test
    fun shouldShowTheEmptyStateWhenThereAreNoItems() {
        composeTestRule.setContent {
            PMovieTheme {
                MainContent(
                    paging = emptyList<Media>().collectAsPreviewLazyPagingItems(),
                    paddingValues = PaddingValues(),
                    onClick = {},
                    media = { it }
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.no_results_found)).assertIsDisplayed()
    }
}
