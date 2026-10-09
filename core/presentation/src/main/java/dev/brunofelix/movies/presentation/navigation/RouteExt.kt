package dev.brunofelix.movies.presentation.navigation

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaType

/**
 * Tabs of the bottom navigation bar, in the order they are shown.
 */
val TopLevelRoutes: List<Route> = listOf(
    Route.Movies,
    Route.TvShows,
    Route.Favorites,
    Route.Releases
)

/**
 * Whether this route is one of the bottom navigation tabs, the only places that show the
 * top bar, the bottom bar and the search overlay.
 */
val Route?.isTopLevel: Boolean
    get() = this != null && this in TopLevelRoutes

/**
 * Detail destination for a media item, picked from its [Media.type].
 */
fun Media.toDetailRoute(): Route = when (type) {
    MediaType.MOVIE -> Route.MovieDetails(id)
    MediaType.TV_SHOW -> Route.TvShowDetails(id)
}
