package dev.brunofelix.movies.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoRootDto(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("results")
    val results: List<VideoDto>? = null
)
