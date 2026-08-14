package dev.brunofelix.movies.feature.search.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.feature.search.presentation.ui.MovieSearchScreen

fun EntryProviderScope<NavKey>.searchEntry(
    paddingValues: PaddingValues
) {
    entry<AppDestination.Search> {
        MovieSearchScreen(
            paddingValues = paddingValues
        )
    }
}
