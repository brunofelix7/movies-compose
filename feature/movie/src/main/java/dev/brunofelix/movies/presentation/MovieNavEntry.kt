package dev.brunofelix.movies.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.presentation.navigation.Route
import dev.brunofelix.movies.presentation.detail.MovieDetailRoute
import dev.brunofelix.movies.presentation.home.MovieHomeRoute

/**
 * @param paddingValues insets of the scaffold that hosts the tabs.
 */
fun EntryProviderScope<NavKey>.movieNavEntry(
    paddingValues: PaddingValues,
    onNavigateToDetails: (Media) -> Unit,
    onNavigateToMediaList: (MediaListCategory) -> Unit,
    onBack: () -> Unit
) {
    entry<Route.Movies> {
        MovieHomeRoute(
            paddingValues = paddingValues,
            onNavigateToDetails = onNavigateToDetails,
            onNavigateToMediaList = onNavigateToMediaList
        )
    }
    entry<Route.MovieDetails> { route ->
        MovieDetailRoute(
            movieId = route.id,
            onBack = onBack
        )
    }
}
