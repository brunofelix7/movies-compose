package dev.brunofelix.movies.core.data.api.dto

import com.google.gson.annotations.SerializedName
import dev.brunofelix.movies.core.domain.model.Genre

data class GenreDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?
) {
    fun toGenre(): Genre {
        return Genre(
            id = id ?: -1,
            name = name ?: "--"
        )
    }
}