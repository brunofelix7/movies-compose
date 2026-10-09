package dev.brunofelix.movies.presentation.model

import androidx.annotation.StringRes
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.model.Category

/**
 * Filter of the favorites list.
 */
enum class FavoriteCategory(
    @param:StringRes override val titleResId: Int,
    val mediaType: MediaType
) : Category {
    MOVIES(R.string.movies, MediaType.MOVIE),
    TV_SHOWS(R.string.tv_shows, MediaType.TV_SHOW)
}
