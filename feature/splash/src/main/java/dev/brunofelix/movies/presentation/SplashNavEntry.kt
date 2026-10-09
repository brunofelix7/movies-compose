package dev.brunofelix.movies.presentation

import androidx.annotation.DrawableRes
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.presentation.navigation.Route

/**
 * @param logoRes logo drawn in the middle of the splash. It comes from the app module so each
 * flavor keeps its own launcher artwork.
 */
fun EntryProviderScope<NavKey>.splashNavEntry(
    @DrawableRes logoRes: Int,
    onFinished: () -> Unit
) {
    entry<Route.Splash> {
        SplashRoute(
            logoRes = logoRes,
            onFinished = onFinished
        )
    }
}
