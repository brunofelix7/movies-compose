package dev.brunofelix.movies.core.domain.model

/**
 * A single episode of a TV show season.
 */
data class Episode(
    val id: Long = 0L,
    val name: String = "",
    val overview: String = "",
    val stillPath: String = "",
    val episodeNumber: Int = 0,
    val seasonNumber: Int = 0,
    val runtime: Int = 0,
    val airDate: String = "",
    val voteAverage: Float = 0f
)
