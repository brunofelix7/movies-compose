package dev.brunofelix.movies.navigation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.brunofelix.movies.HiltTestActivity
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.presentation.navigation.Route
import dev.brunofelix.movies.presentation.SPLASH_SCREEN_TEST_TAG
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import dev.brunofelix.movies.core.presentation.R as PresentationR
import dev.brunofelix.movies.feature.favorite.R as FavoriteR
import dev.brunofelix.movies.feature.settings.R as SettingsR

private const val TIMEOUT_MILLIS = 5_000L

@HiltAndroidTest
class NavigationGraphTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    private fun render(route: Route) {
        composeTestRule.setContent {
            PMovieTheme {
                NavigationGraph(
                    backStack = listOf(route),
                    onNavigate = {},
                    onReplace = {},
                    onBack = {}
                )
            }
        }
    }

    private fun waitForText(text: String) {
        composeTestRule.waitUntil(TIMEOUT_MILLIS) {
            composeTestRule.onAllNodes(hasText(text)).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun waitForContentDescription(description: String) {
        composeTestRule.waitUntil(TIMEOUT_MILLIS) {
            composeTestRule.onAllNodes(hasContentDescription(description)).fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun shouldRenderSplashScreenWhenRouteIsSplash() {
        render(Route.Splash)

        composeTestRule.onNodeWithTag(SPLASH_SCREEN_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun shouldRenderMovieHomeWhenRouteIsMovies() {
        render(Route.Movies)

        waitForContentDescription("Dune")
        composeTestRule.onNodeWithText(context.getString(PresentationR.string.upcoming)).assertIsDisplayed()
    }

    @Test
    fun shouldRenderTvShowHomeWhenRouteIsTvShows() {
        render(Route.TvShows)

        waitForContentDescription("Dark")
        composeTestRule.onNodeWithText(context.getString(PresentationR.string.top_rated)).assertIsDisplayed()
    }

    @Test
    fun shouldRenderFavoritesWhenRouteIsFavorites() {
        render(Route.Favorites)

        waitForText(context.getString(FavoriteR.string.favorites_empty))
        composeTestRule.onNodeWithText(context.getString(PresentationR.string.tv_shows)).assertIsDisplayed()
    }

    @Test
    fun shouldRenderReleasesWhenRouteIsReleases() {
        render(Route.Releases)

        waitForText(context.getString(PresentationR.string.releases_theaters))
        composeTestRule.onNodeWithText(context.getString(PresentationR.string.releases_theaters)).assertIsDisplayed()
    }

    @Test
    fun shouldRenderSettingsWhenRouteIsSettings() {
        render(Route.Settings)

        waitForText("1.0.0-test")
        composeTestRule.onNodeWithText(context.getString(SettingsR.string.settings)).assertIsDisplayed()
    }

    @Test
    fun shouldRenderMovieDetailsWhenRouteIsMovieDetails() {
        render(Route.MovieDetails(id = 1L))

        waitForText("A desert planet.")
    }

    @Test
    fun shouldRenderTvShowDetailsWhenRouteIsTvShowDetails() {
        render(Route.TvShowDetails(id = 2L))

        waitForText("A missing child.")
    }

    @Test
    fun shouldRenderMediaListWhenRouteIsMediaList() {
        render(Route.MediaList(category = MediaListCategory.MOVIE_TOP_RATED))

        waitForContentDescription("Dune")
        composeTestRule.onNodeWithText(context.getString(PresentationR.string.top_rated)).assertIsDisplayed()
    }
}
