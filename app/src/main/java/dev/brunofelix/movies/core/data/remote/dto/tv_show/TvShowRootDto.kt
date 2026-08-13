package dev.brunofelix.movies.core.data.remote.dto.tv_show

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object representing the root response for TV show list requests.
 *
 * @property page The current page number of the results.
 * @property results List of [TvShowDto] objects for the current page.
 * @property totalPages The total number of pages available.
 * @property totalResults The total number of results available.
 */
data class TvShowRootDto(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: List<TvShowDto>?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?
)
