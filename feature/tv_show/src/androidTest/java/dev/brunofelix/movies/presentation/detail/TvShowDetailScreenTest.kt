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
import dev.brunofelix.movies.presentation.model.EpisodeUiModel
import dev.brunofelix.movies.presentation.model.SeasonUiModel
import dev.brunofelix.movies.presentation.model.TvShowUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.feature.tv_show.R
import dev.brunofelix.movies.presentation.detail.components.SeasonsSection
import dev.brunofelix.movies.presentation.detail.components.TvShowDetailContent
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

private val seasons = listOf(
    SeasonUiModel(id = 1L, name = "Season 1", seasonNumber = 1, episodeCount = 10, airYear = "2017"),
    SeasonUiModel(id = 2L, name = "Season 2", seasonNumber = 2, episodeCount = 8, airYear = "2019")
)

private val tvShow = TvShowUiModel(
    id = 3L,
    name = "Dark",
    overview = "A missing child.",
    firstAirDate = "01/12/2017",
    voteAverage = "8.4",
    numberOfSeasons = 2,
    numberOfEpisodes = 18,
    seasons = seasons
)

private val episodes = listOf(EpisodeUiModel(id = 11L, name = "Secrets", episodeNumber = 1, runtime = "51min"))

@RunWith(AndroidJUnit4::class)
class TvShowDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheSkeletonWhenStateIsLoading() {
        composeTestRule.setContent {
            PMovieTheme { TvShowDetailScreen(uiState = TvShowDetailUiState(tvShow = UiState.Loading), onAction = {}) }
        }

        composeTestRule.onNodeWithTag(DETAIL_SKELETON_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldRenderTheTvShowWhenStateIsSuccess() {
        composeTestRule.setContent {
            PMovieTheme { TvShowDetailScreen(uiState = TvShowDetailUiState(tvShow = UiState.Success(tvShow)), onAction = {}) }
        }

        composeTestRule.onNodeWithText("8.4").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.seasons, 2)).performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.seasons_title)).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun shouldRenderTheEmptyStateWhenStateIsEmpty() {
        composeTestRule.setContent {
            PMovieTheme { TvShowDetailScreen(uiState = TvShowDetailUiState(tvShow = UiState.Empty), onAction = {}) }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.no_results_found)).assertIsDisplayed()
    }

    @Test
    fun shouldTriggerRetryWhenStateIsError() {
        var capturedAction: TvShowDetailUiAction? = null
        composeTestRule.setContent {
            PMovieTheme {
                TvShowDetailScreen(
                    uiState = TvShowDetailUiState(tvShow = UiState.Error(UiText.DynamicString("No internet"))),
                    onAction = { capturedAction = it }
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.retry)).performClick()

        capturedAction shouldBe TvShowDetailUiAction.OnRetry
    }

    @Test
    fun shouldTriggerBackFavoriteAndSeasonActions() {
        val actions = mutableListOf<TvShowDetailUiAction>()
        composeTestRule.setContent {
            PMovieTheme {
                TvShowDetailScreen(uiState = TvShowDetailUiState(tvShow = UiState.Success(tvShow)), onAction = { actions += it })
            }
        }

        composeTestRule.onNodeWithContentDescription(context.getString(DesignSystemR.string.top_bar_back_icon)).performClick()
        composeTestRule.onNodeWithContentDescription(context.getString(DesignSystemR.string.top_bar_favorite_icon)).performClick()
        composeTestRule.onNodeWithText("Season 2").performScrollTo().performClick()

        actions shouldBe listOf(
            TvShowDetailUiAction.OnBack,
            TvShowDetailUiAction.OnFavoriteToggle,
            TvShowDetailUiAction.OnSeasonToggle(2)
        )
    }
}

@RunWith(AndroidJUnit4::class)
class SeasonsSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheEpisodesOfTheExpandedSeason() {
        composeTestRule.setContent {
            PMovieTheme {
                SeasonsSection(
                    seasons = seasons,
                    state = SeasonsState(expandedSeasonNumber = 1, episodes = mapOf(1 to UiState.Success(episodes)))
                )
            }
        }

        composeTestRule.onNodeWithText("Secrets").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.episode_number, 1)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.season_collapse)).assertIsDisplayed()
    }

    @Test
    fun shouldReportToggleAndRetry() {
        val toggled = mutableListOf<Int>()
        val retried = mutableListOf<Int>()
        composeTestRule.setContent {
            PMovieTheme {
                SeasonsSection(
                    seasons = seasons,
                    state = SeasonsState(
                        expandedSeasonNumber = 1,
                        episodes = mapOf(1 to UiState.Error(UiText.DynamicString("Error")))
                    ),
                    onSeasonToggle = { toggled += it },
                    onSeasonRetry = { retried += it }
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(DesignSystemR.string.retry)).performClick()
        composeTestRule.onNodeWithText("Season 2").performClick()

        retried shouldBe listOf(1)
        toggled shouldBe listOf(2)
    }

    @Test
    fun shouldRenderNothingWithoutSeasons() {
        composeTestRule.setContent {
            PMovieTheme { SeasonsSection(seasons = emptyList(), state = SeasonsState()) }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.seasons_title)).assertDoesNotExist()
    }
}

@RunWith(AndroidJUnit4::class)
class TvShowDetailContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun shouldRenderTheTvShowInformation() {
        composeTestRule.setContent {
            PMovieTheme { TvShowDetailContent(tvShow = tvShow, seasonsState = SeasonsState()) }
        }

        composeTestRule.onNodeWithText("01/12/2017").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.episodes, 18)).performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Season 1").performScrollTo().assertIsDisplayed()
    }
}
