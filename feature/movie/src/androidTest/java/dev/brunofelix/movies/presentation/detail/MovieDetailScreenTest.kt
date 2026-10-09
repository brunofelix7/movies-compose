package dev.brunofelix.movies.presentation.detail

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.brunofelix.movies.designsystem.components.DETAIL_SKELETON_TEST_TAG
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.MovieGenre
import dev.brunofelix.movies.presentation.model.CastUiModel
import dev.brunofelix.movies.presentation.model.MovieUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.detail.components.MovieDetailContent
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR
import dev.brunofelix.movies.core.presentation.R as PresentationR

private val movie = MovieUiModel(
    id = 7L,
    title = "Dune",
    overview = "A desert planet.",
    releaseDate = "22/10/2021",
    voteAverage = "8.1",
    duration = "2h 35m",
    genres = listOf(MovieGenre(name = "Science Fiction")),
    cast = listOf(CastUiModel(id = 1L, name = "Zendaya", character = "Chani"))
)

@RunWith(AndroidJUnit4::class)
class MovieDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheSkeletonWhenStateIsLoading() {
        composeTestRule.setContent {
            PMovieTheme { MovieDetailScreen(uiState = MovieDetailUiState(movie = UiState.Loading), onAction = {}) }
        }

        composeTestRule.onNodeWithTag(DETAIL_SKELETON_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldRenderTheMovieWhenStateIsSuccess() {
        composeTestRule.setContent {
            PMovieTheme { MovieDetailScreen(uiState = MovieDetailUiState(movie = UiState.Success(movie)), onAction = {}) }
        }

        composeTestRule.onNodeWithText("A desert planet.").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("8.1").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Science Fiction").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun shouldRenderTheEmptyStateWhenStateIsEmpty() {
        composeTestRule.setContent {
            PMovieTheme { MovieDetailScreen(uiState = MovieDetailUiState(movie = UiState.Empty), onAction = {}) }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.no_results_found)).assertIsDisplayed()
    }

    @Test
    fun shouldTriggerRetryWhenStateIsError() {
        var capturedAction: MovieDetailUiAction? = null
        composeTestRule.setContent {
            PMovieTheme {
                MovieDetailScreen(
                    uiState = MovieDetailUiState(movie = UiState.Error(UiText.DynamicString("No internet"))),
                    onAction = { capturedAction = it }
                )
            }
        }

        composeTestRule.onNodeWithText("No internet").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.retry)).performClick()

        capturedAction shouldBe MovieDetailUiAction.OnRetry
    }

    @Test
    fun shouldTriggerBackAndFavoriteActions() {
        val actions = mutableListOf<MovieDetailUiAction>()
        composeTestRule.setContent {
            PMovieTheme {
                MovieDetailScreen(uiState = MovieDetailUiState(movie = UiState.Success(movie)), onAction = { actions += it })
            }
        }

        composeTestRule.onNodeWithContentDescription(context.getString(DesignSystemR.string.top_bar_back_icon)).performClick()
        composeTestRule.onNodeWithContentDescription(context.getString(DesignSystemR.string.top_bar_favorite_icon)).performClick()

        actions shouldBe listOf(MovieDetailUiAction.OnBack, MovieDetailUiAction.OnFavoriteToggle)
    }
}

@RunWith(AndroidJUnit4::class)
class MovieDetailContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheMovieInformationAndCast() {
        composeTestRule.setContent {
            PMovieTheme { MovieDetailContent(movie = movie) }
        }

        composeTestRule.onNodeWithText("22/10/2021").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("2h 35m").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(PresentationR.string.overview)).performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(PresentationR.string.trailer)).assertDoesNotExist()
    }
}
