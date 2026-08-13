package dev.brunofelix.movies.core.data.remote.dto.movie

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object representing a movie or TV show genre.
 *
 * @property id Unique identifier for the genre.
 * @property name The name of the genre (e.g., Action, Comedy).
 */
data class MovieGenreDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?
)
