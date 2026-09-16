package dev.brunofelix.movies.core.domain.model

/**
 * A season of a TV show. Episodes are loaded on demand, so they are not part of the model
 * returned with the TV show details.
 */
data class Season(
    val id: Long = 0L,
    val name: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val seasonNumber: Int = 0,
    val episodeCount: Int = 0,
    val airDate: String = "",
    val voteAverage: Float = 0f
)
