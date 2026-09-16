package dev.brunofelix.movies.core.presentation.navigation

import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.domain.model.enums.MediaListCategory
import kotlinx.serialization.Serializable

sealed interface MainNavKey : NavKey {

    @Serializable
    data object Movies : MainNavKey

    @Serializable
    data object TvShows : MainNavKey

    @Serializable
    data object Favorites : MainNavKey

    @Serializable
    data object Releases : MainNavKey

    @Serializable
    data object Settings : MainNavKey

    @Serializable
    data class MovieDetails(val id: Long) : MainNavKey

    @Serializable
    data class TvShowDetails(val id: Long) : MainNavKey

    /**
     * Full, paginated version of one of the home rows.
     *
     * @param month `yyyy-MM` when the category is a monthly release list, null otherwise.
     */
    @Serializable
    data class MediaList(
        val category: MediaListCategory,
        val month: String? = null
    ) : MainNavKey
}
