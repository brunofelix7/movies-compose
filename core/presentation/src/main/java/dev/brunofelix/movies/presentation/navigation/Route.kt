package dev.brunofelix.movies.presentation.navigation

import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {
    @Serializable
    data object Splash : Route

    @Serializable
    data object Movies : Route

    @Serializable
    data object TvShows : Route

    @Serializable
    data object Favorites : Route

    @Serializable
    data object Releases : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data class MovieDetails(val id: Long) : Route

    @Serializable
    data class TvShowDetails(val id: Long) : Route

    /**
     * Full, paginated version of one of the home rows.
     *
     * @param month `yyyy-MM` when the category is a monthly release list, null otherwise.
     */
    @Serializable
    data class MediaList(
        val category: MediaListCategory,
        val month: String? = null
    ) : Route
}
