package dev.brunofelix.movies.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.presentation.navigation.Route

/**
 * @param paddingValues insets of the scaffold that hosts the tabs.
 * @param onNavigateToMediaList receives the list category and the `yyyy-MM` month it belongs to.
 */
fun EntryProviderScope<NavKey>.releaseNavEntry(
    paddingValues: PaddingValues,
    onNavigateToDetails: (Media) -> Unit,
    onNavigateToMediaList: (MediaListCategory, String) -> Unit
) {
    entry<Route.Releases> {
        ReleaseRoute(
            paddingValues = paddingValues,
            onNavigateToDetails = onNavigateToDetails,
            onNavigateToMediaList = onNavigateToMediaList
        )
    }
}
