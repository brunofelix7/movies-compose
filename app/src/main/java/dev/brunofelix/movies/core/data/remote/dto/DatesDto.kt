package dev.brunofelix.movies.core.data.remote.dto

import com.google.gson.annotations.SerializedName
import dev.brunofelix.movies.core.domain.model.Dates

data class DatesDto(
    @SerializedName("maximum")
    val maximum: String?,

    @SerializedName("minimum")
    val minimum: String?
) {
    fun toDates(): Dates {
        return Dates(
            maximum = maximum ?: "",
            minimum = minimum ?: ""
        )
    }
}