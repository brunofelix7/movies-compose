package dev.brunofelix.movies.feature.movie.home.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.feature.movie.home.presentation.ui.MovieHomeScreen

fun EntryProviderScope<NavKey>.movieHomeEntry(
    onNavigate: (MainNavKey) -> Unit,
    paddingValues: PaddingValues
) {
    entry<MainNavKey.Movies> {
        MovieHomeScreen(
            onItemClick = { movieId ->
                onNavigate(MainNavKey.MovieDetails(movieId))
            },
            paddingValues = paddingValues
        )
    }
}
