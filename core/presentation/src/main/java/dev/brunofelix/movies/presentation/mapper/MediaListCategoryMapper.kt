package dev.brunofelix.movies.presentation.mapper

import androidx.annotation.StringRes
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.core.presentation.R

/**
 * Title shown in the top bar of the full list opened from a "View more" action.
 */
@get:StringRes
val MediaListCategory.titleResId: Int
    get() = when (this) {
        MediaListCategory.MOVIE_POPULAR -> R.string.popular
        MediaListCategory.MOVIE_UPCOMING -> R.string.upcoming
        MediaListCategory.MOVIE_TOP_RATED -> R.string.top_rated
        MediaListCategory.TV_SHOW_POPULAR -> R.string.popular
        MediaListCategory.TV_SHOW_TOP_RATED -> R.string.top_rated
        MediaListCategory.RELEASE_THEATERS -> R.string.releases_theaters
        MediaListCategory.RELEASE_STREAMING -> R.string.releases_streaming
        MediaListCategory.RELEASE_SERIES -> R.string.releases_series
    }
