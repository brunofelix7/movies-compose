package dev.brunofelix.movies.core.domain.model.enums

import androidx.annotation.StringRes
import dev.brunofelix.movies.R

interface Category {
    @get:StringRes
    val titleResId: Int
}

enum class MovieCategory(@StringRes override val titleResId: Int) : Category {
    POPULAR(R.string.popular),
    UPCOMING(R.string.upcoming),
    TOP_RATED(R.string.top_rated)
}

enum class TvShowCategory(@StringRes override val titleResId: Int) : Category {
    POPULAR(R.string.popular),
    TOP_RATED(R.string.top_rated)
}
