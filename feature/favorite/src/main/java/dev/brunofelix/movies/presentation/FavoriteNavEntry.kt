package dev.brunofelix.movies.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.presentation.navigation.Route

/**
 * @param paddingValues insets of the scaffold that hosts the tabs.
 */
fun EntryProviderScope<NavKey>.favoriteNavEntry(
    paddingValues: PaddingValues,
    onNavigateToDetails: (Media) -> Unit
) {
    entry<Route.Favorites> {
        FavoriteRoute(
            paddingValues = paddingValues,
            onNavigateToDetails = onNavigateToDetails
        )
    }
}
