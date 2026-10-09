package dev.brunofelix.movies.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Offers of a single region, grouped by how they are monetized. The `rent` and `buy` groups
 * are left out on purpose: only services where the title is part of the catalog count.
 */
@Serializable
data class WatchProviderRegionDto(
    @SerialName("link")
    val link: String? = null,

    @SerialName("flatrate")
    val flatrate: List<WatchProviderDto>? = null,

    @SerialName("free")
    val free: List<WatchProviderDto>? = null,

    @SerialName("ads")
    val ads: List<WatchProviderDto>? = null
)
