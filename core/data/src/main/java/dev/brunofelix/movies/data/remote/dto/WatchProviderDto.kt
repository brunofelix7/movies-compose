package dev.brunofelix.movies.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchProviderDto(
    @SerialName("provider_id")
    val providerId: Long? = null,

    @SerialName("provider_name")
    val providerName: String? = null,

    @SerialName("logo_path")
    val logoPath: String? = null,

    @SerialName("display_priority")
    val displayPriority: Int? = null
)
