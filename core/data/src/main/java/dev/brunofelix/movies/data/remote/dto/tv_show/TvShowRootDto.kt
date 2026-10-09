package dev.brunofelix.movies.data.remote.dto.tv_show

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing the root response for TV show list requests.
 *
 * @property page The current page number of the results.
 * @property results List of [TvShowDto] objects for the current page.
 * @property totalPages The total number of pages available.
 * @property totalResults The total number of results available.
 */
@Serializable
data class TvShowRootDto(
    @SerialName("page")
    val page: Int? = null,

    @SerialName("results")
    val results: List<TvShowDto>? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("total_results")
    val totalResults: Int? = null
)
