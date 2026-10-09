package dev.brunofelix.movies.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.NavDisplay
import dev.brunofelix.movies.R
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.presentation.navigation.Route
import dev.brunofelix.movies.presentation.navigation.toDetailRoute
import dev.brunofelix.movies.presentation.favoriteNavEntry
import dev.brunofelix.movies.presentation.mediaListNavEntry
import dev.brunofelix.movies.presentation.movieNavEntry
import dev.brunofelix.movies.presentation.releaseNavEntry
import dev.brunofelix.movies.presentation.settingsNavEntry
import dev.brunofelix.movies.presentation.splashNavEntry
import dev.brunofelix.movies.presentation.tvShowNavEntry

private const val POP_DURATION_MILLIS = 700

/**
 * Crossfade used for every pop, no matter what triggered it.
 *
 * Navigation 3 animates a predictive back with a spec of its own, which scales the outgoing
 * screen down to 70%, so the system back button and gesture looked nothing like the back arrow
 * in the app. Feeding this to both specs keeps the three of them identical.
 */
private val PopTransition: AnimatedContentTransitionScope<Scene<NavKey>>.() -> ContentTransform = {
    ContentTransform(
        targetContentEnter = fadeIn(animationSpec = tween(POP_DURATION_MILLIS)),
        initialContentExit = fadeOut(animationSpec = tween(POP_DURATION_MILLIS))
    )
}

/**
 * @param paddingValues insets of the scaffold that hosts the tabs, passed down to them.
 */
@Composable
fun NavigationGraph(
    backStack: List<Route>,
    onNavigate: (Route) -> Unit,
    onReplace: (Route) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    val onNavigateToDetails = { media: Media -> onNavigate(media.toDetailRoute()) }
    val onNavigateToMediaList = { category: MediaListCategory -> onNavigate(Route.MediaList(category = category)) }

    val entryProvider = entryProvider {
        splashNavEntry(
            logoRes = R.mipmap.ic_launcher_foreground,
            onFinished = { onReplace(Route.Movies) }
        )
        movieNavEntry(
            paddingValues = paddingValues,
            onNavigateToDetails = onNavigateToDetails,
            onNavigateToMediaList = onNavigateToMediaList,
            onBack = onBack
        )
        tvShowNavEntry(
            paddingValues = paddingValues,
            onNavigateToDetails = onNavigateToDetails,
            onNavigateToMediaList = onNavigateToMediaList,
            onBack = onBack
        )
        favoriteNavEntry(
            paddingValues = paddingValues,
            onNavigateToDetails = onNavigateToDetails
        )
        releaseNavEntry(
            paddingValues = paddingValues,
            onNavigateToDetails = onNavigateToDetails,
            onNavigateToMediaList = { category, month ->
                onNavigate(Route.MediaList(category = category, month = month))
            }
        )
        settingsNavEntry(onBack = onBack)
        mediaListNavEntry(
            onNavigateToDetails = onNavigateToDetails,
            onBack = onBack
        )
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        entryProvider = entryProvider,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        popTransitionSpec = PopTransition,
        predictivePopTransitionSpec = { PopTransition(this) }
    )
}
