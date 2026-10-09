package dev.brunofelix.movies.presentation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.presentation.navigation.Route

fun EntryProviderScope<NavKey>.mediaListNavEntry(
    onNavigateToDetails: (Media) -> Unit,
    onBack: () -> Unit
) {
    entry<Route.MediaList> { route ->
        MediaListRoute(
            category = route.category,
            month = ReleaseMonth.from(route.month),
            onNavigateToDetails = onNavigateToDetails,
            onBack = onBack
        )
    }
}
