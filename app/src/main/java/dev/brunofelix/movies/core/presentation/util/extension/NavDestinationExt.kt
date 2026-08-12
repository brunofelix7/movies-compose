package dev.brunofelix.movies.core.presentation.util.extension

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import dev.brunofelix.movies.core.presentation.navigation.AppDestination

val NavDestination?.shouldShowBottomBar: Boolean
    get() = this?.let { dest ->
        val topLevelRoutes = listOf(
            AppDestination.Movies::class,
            AppDestination.TvShows::class,
            AppDestination.Search::class,
            AppDestination.Favorites::class,
        )
        topLevelRoutes.any { route -> dest.hasRoute(route) }
    } ?: false