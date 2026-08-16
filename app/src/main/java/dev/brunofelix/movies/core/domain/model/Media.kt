package dev.brunofelix.movies.core.domain.model

import dev.brunofelix.movies.core.domain.model.enums.MediaType

/**
 * Represents a media item, such as a movie or TV show.
 */
data class Media(
    val id: Long,
    val title: String,
    val posterPath: String,
    val voteAverage: Float,
    val releaseDate: String,
    val overview: String,
    val type: MediaType
)