package dev.brunofelix.movies.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response of the `watch/providers` endpoints. [results] is keyed by ISO 3166-1 region code
 * (e.g. `BR`), and a region missing from it means no offer there.
 */
@Serializable
data class WatchProvidersRootDto(
    @SerialName("id")
    val id: Long? = null,

    @SerialName("results")
    val results: Map<String, WatchProviderRegionDto>? = null
)
