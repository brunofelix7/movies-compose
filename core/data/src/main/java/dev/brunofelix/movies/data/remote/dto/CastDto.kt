package dev.brunofelix.movies.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing an actor credited in a movie or TV show.
 *
 * @property id Unique identifier for the person.
 * @property name The actor's name.
 * @property character The name of the character played.
 * @property profilePath Relative path to the actor's photo.
 * @property order The billing position, where lower values are top billed.
 */
@Serializable
data class CastDto(
    @SerialName("id")
    val id: Long? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("character")
    val character: String? = null,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("order")
    val order: Int? = null
)
