package dev.brunofelix.movies.core.domain.model.enums

import androidx.annotation.StringRes
import dev.brunofelix.movies.R

/**
 * Represents a category of media, such as popular, upcoming, or top-rated.
 */
interface Category {
    @get:StringRes
    val titleResId: Int
}

/**
 * Represents a category of movies, such as popular, upcoming, or top-rated.
 */
enum class MovieCategory(@StringRes override val titleResId: Int) : Category {
    POPULAR(R.string.popular),
    UPCOMING(R.string.upcoming),
    TOP_RATED(R.string.top_rated)
}

/**
 * Represents a category of TV shows, such as popular or top-rated.
 */
enum class TvShowCategory(@StringRes override val titleResId: Int) : Category {
    POPULAR(R.string.popular),
    TOP_RATED(R.string.top_rated)
}
