package dev.brunofelix.movies.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsRootDto(
    @SerialName("id")
    val id: Long? = null,

    @SerialName("cast")
    val cast: List<CastDto>? = null
)
