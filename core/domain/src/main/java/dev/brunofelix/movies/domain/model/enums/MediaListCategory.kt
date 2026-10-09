package dev.brunofelix.movies.domain.model.enums

/**
 * Every list that can be opened in full from a "View more" action.
 */
enum class MediaListCategory {
    MOVIE_POPULAR,
    MOVIE_UPCOMING,
    MOVIE_TOP_RATED,
    TV_SHOW_POPULAR,
    TV_SHOW_TOP_RATED,
    RELEASE_THEATERS,
    RELEASE_STREAMING,
    RELEASE_SERIES;

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
