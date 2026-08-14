package dev.brunofelix.movies.feature.movie.detail.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.core.presentation.navigation.Navigator
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.MovieDetailRoute

fun EntryProviderScope<NavKey>.detailGraph(
    navigator: Navigator
) {
    entry<AppDestination.Details> { key ->
        MovieDetailRoute(
            movieId = key.id,
            navigator = navigator
        )
    }
}
