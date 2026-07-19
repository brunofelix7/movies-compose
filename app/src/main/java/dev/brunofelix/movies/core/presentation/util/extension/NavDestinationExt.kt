package dev.brunofelix.movies.core.presentation.util.extension

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import dev.brunofelix.movies.core.presentation.navigation.MovieDestination

val NavDestination?.shouldShowBottomBar: Boolean
    get() = this?.let { dest ->
        val topLevelRoutes = listOf(
            MovieDestination.Populars::class,
            MovieDestination.Upcoming::class,
            MovieDestination.Favorites::class,
        )
        topLevelRoutes.any { route -> dest.hasRoute(route) }
    } ?: false