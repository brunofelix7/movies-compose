package dev.brunofelix.movies.core.presentation.navigation

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.enums.MediaType

/**
 * Detail destination for a media item, picked from its [Media.type].
 */
fun Media.toDetailNavKey(): MainNavKey = when (type) {
    MediaType.MOVIE -> MainNavKey.MovieDetails(id)
    MediaType.TV_SHOW -> MainNavKey.TvShowDetails(id)
}
