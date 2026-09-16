package dev.brunofelix.movies.core.domain.model.enums

import androidx.annotation.StringRes
import dev.brunofelix.movies.R
import kotlinx.serialization.Serializable

/**
 * Every list that can be opened in full from a "View more" action. The [titleResId] is what
 * the list screen shows in its top bar.
 */
@Serializable
enum class MediaListCategory(@StringRes val titleResId: Int) {
    MOVIE_POPULAR(R.string.popular),
    MOVIE_UPCOMING(R.string.upcoming),
    MOVIE_TOP_RATED(R.string.top_rated),
    TV_SHOW_POPULAR(R.string.popular),
    TV_SHOW_TOP_RATED(R.string.top_rated),
    RELEASE_THEATERS(R.string.releases_theaters),
    RELEASE_STREAMING(R.string.releases_streaming),
    RELEASE_SERIES(R.string.releases_series);

    val mediaType: MediaType
        get() = when (this) {
            MOVIE_POPULAR,
            MOVIE_UPCOMING,
            MOVIE_TOP_RATED,
            RELEASE_THEATERS,
            RELEASE_STREAMING -> MediaType.MOVIE
            TV_SHOW_POPULAR,
            TV_SHOW_TOP_RATED,
            RELEASE_SERIES -> MediaType.TV_SHOW
        }
}
