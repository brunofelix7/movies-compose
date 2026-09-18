package dev.brunofelix.movies.core.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object representing an actor credited in a movie or TV show.
 *
 * @property id Unique identifier for the person.
 * @property name The actor's name.
 * @property character The name of the character played.
 * @property profilePath Relative path to the actor's photo.
 * @property order The billing position, where lower values are top billed.
 */
data class CastDto(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("character")
    val character: String?,

    @SerializedName("profile_path")
    val profilePath: String?,

    @SerializedName("order")
    val order: Int?
)
