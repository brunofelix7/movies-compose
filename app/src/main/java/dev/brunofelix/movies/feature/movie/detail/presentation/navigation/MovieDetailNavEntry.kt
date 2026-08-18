package dev.brunofelix.movies.feature.movie.detail.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.navigation.Navigator
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.MovieDetailRoute

fun EntryProviderScope<NavKey>.movieDetailEntry(
    navigator: Navigator
) {
    entry<MainNavKey.MovieDetails> { key ->
        MovieDetailRoute(
            movieId = key.id,
            navigator = navigator
        )
    }
}