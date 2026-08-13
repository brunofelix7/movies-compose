package dev.brunofelix.movies.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieGenreDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?
)
