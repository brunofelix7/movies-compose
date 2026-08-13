package dev.brunofelix.movies.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDateDto(
    @SerializedName("maximum")
    val maximum: String?,

    @SerializedName("minimum")
    val minimum: String?
)
