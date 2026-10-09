package dev.brunofelix.movies.data.local.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Streaming service stored as JSON in the `watchProviders` column of [MediaEntity], not a
 * table of its own: it is only ever read together with its media.
 */
@Serializable
data class WatchProviderEntity(
    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String,

    @SerialName("logoPath")
    val logoPath: String
)
