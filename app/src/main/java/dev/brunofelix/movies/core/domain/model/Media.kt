package dev.brunofelix.movies.core.domain.model

import dev.brunofelix.movies.core.domain.model.enums.MediaType

/**
 * Represents a media item, such as a movie or TV show.
 */
data class Media(
    val id: Long = 0L,
    val title: String = "",
    val posterPath: String = "",
    val voteAverage: Float = 0F,
    val releaseDate: String = "",
    val duration: Int = 0,
    val type: MediaType = MediaType.MOVIE
)