package dev.brunofelix.movies.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreditsRootDto(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("cast")
    val cast: List<CastDto>?
)
