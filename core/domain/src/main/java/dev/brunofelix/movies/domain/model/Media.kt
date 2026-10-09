package dev.brunofelix.movies.domain.model

import dev.brunofelix.movies.domain.model.enums.MediaType

/**
 * Represents a media item, such as a movie or TV show.
 *
 * @property watchProviders Streaming services that offer it. `null` means they were never
 * fetched, which is different from an empty list (fetched, but on no service).
 */
data class Media(
    val id: Long = 0L,
    val title: String = "",
    val posterPath: String = "",
    val voteAverage: Float = 0F,
    val releaseDate: String = "",
    val duration: Int = 0,
    val type: MediaType = MediaType.MOVIE,
    val watchProviders: List<WatchProvider>? = null
)