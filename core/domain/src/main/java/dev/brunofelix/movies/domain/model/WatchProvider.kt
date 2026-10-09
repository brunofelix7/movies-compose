package dev.brunofelix.movies.domain.model

/**
 * A streaming service that offers a movie or TV show in the user's region.
 */
data class WatchProvider(
    val id: Long = 0L,
    val name: String = "",
    val logoPath: String = ""
)
