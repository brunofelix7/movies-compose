package dev.brunofelix.movies.data.remote.dto.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing a movie or TV show genre.
 *
 * @property id Unique identifier for the genre.
 * @property name The name of the genre (e.g., Action, Comedy).
 */
@Serializable
data class MovieGenreDto(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("name")
    val name: String? = null
)
