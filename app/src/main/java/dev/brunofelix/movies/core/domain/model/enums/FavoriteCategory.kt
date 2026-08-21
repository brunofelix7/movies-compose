package dev.brunofelix.movies.core.domain.model.enums

import androidx.annotation.StringRes
import dev.brunofelix.movies.R

/**
 * Represents a category for filtering favorite items.
 */
enum class FavoriteCategory(
    @StringRes override val titleResId: Int
) : Category {
    MOVIES(R.string.movies),
    TV_SHOWS(R.string.tv_shows)
}
