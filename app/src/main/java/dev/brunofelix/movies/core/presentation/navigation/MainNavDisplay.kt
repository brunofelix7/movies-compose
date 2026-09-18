package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.NavDisplay
import dev.brunofelix.movies.feature.favorite.presentation.navigation.favoriteEntry
import dev.brunofelix.movies.feature.media_list.presentation.navigation.mediaListEntry
import dev.brunofelix.movies.feature.movie.detail.presentation.navigation.movieDetailEntry
import dev.brunofelix.movies.feature.movie.home.presentation.navigation.movieHomeEntry
import dev.brunofelix.movies.feature.release.presentation.navigation.releaseEntry
import dev.brunofelix.movies.feature.settings.presentation.navigation.settingsEntry
import dev.brunofelix.movies.feature.tv_show.detail.presentation.navigation.tvShowDetailEntry
import dev.brunofelix.movies.feature.tv_show.home.presentation.navigation.tvShowHomeEntry

private const val PopDurationMillis = 700

/**
 * Crossfade used for every pop, no matter what triggered it.
 *
 * Navigation 3 animates a predictive back with a spec of its own, which scales the outgoing
 * screen down to 70%, so the system back button and gesture looked nothing like the back arrow
 * in the app. Feeding this to both specs keeps the three of them identical.
 */
private val PopTransition: AnimatedContentTransitionScope<Scene<NavKey>>.() -> ContentTransform = {
    ContentTransform(
        targetContentEnter = fadeIn(animationSpec = tween(PopDurationMillis)),
        initialContentExit = fadeOut(animationSpec = tween(PopDurationMillis))
    )
}

@Composable
fun MainNavDisplay(
    backStack: List<MainNavKey>,
    onNavigate: (MainNavKey) -> Unit,
    onBack: () -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        // Movies Screen
        movieHomeEntry(onNavigate, paddingValues)

        // TV Shows Screen
        tvShowHomeEntry(onNavigate, paddingValues)

        // Favorites Screen
        favoriteEntry(onNavigate, paddingValues)

        // Releases Screen
        releaseEntry(onNavigate, paddingValues)

        // Settings Screen
        settingsEntry(onBack)

        // Movie Details Screen
        movieDetailEntry(onBack)

        // TV Show Details Screen
        tvShowDetailEntry(onBack)

        // Full, paginated version of a home row
        mediaListEntry(onNavigate, onBack)
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        popTransitionSpec = PopTransition,
        predictivePopTransitionSpec = { PopTransition(this) },
        entryProvider = entryProvider
    )
}
