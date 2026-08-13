package dev.brunofelix.movies.core.data.remote.dto.movie

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object representing a date range for movie releases.
 *
 * @property maximum The maximum date in the range (YYYY-MM-DD).
 * @property minimum The minimum date in the range (YYYY-MM-DD).
 */
data class MovieDateDto(
    @SerializedName("maximum")
    val maximum: String?,

    @SerializedName("minimum")
    val minimum: String?
)
