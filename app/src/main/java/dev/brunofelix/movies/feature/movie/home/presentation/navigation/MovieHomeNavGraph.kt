package dev.brunofelix.movies.feature.movie.home.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.core.presentation.navigation.Navigator
import dev.brunofelix.movies.feature.movie.home.presentation.ui.MovieHomeScreen

fun EntryProviderScope<NavKey>.movieHomeGraph(
    navigator: Navigator,
    paddingValues: PaddingValues
) {
    entry<AppDestination.Movies> {
        MovieHomeScreen(
            onItemClick = { movieId ->
                navigator.navigate(AppDestination.Details(movieId))
            },
            paddingValues = paddingValues
        )
    }
}
