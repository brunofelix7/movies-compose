package dev.brunofelix.movies.data.remote.dto.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing a date range for movie releases.
 *
 * @property maximum The maximum date in the range (YYYY-MM-DD).
 * @property minimum The minimum date in the range (YYYY-MM-DD).
 */
@Serializable
data class MovieDateDto(
    @SerialName("maximum")
    val maximum: String? = null,

    @SerialName("minimum")
    val minimum: String? = null
)
