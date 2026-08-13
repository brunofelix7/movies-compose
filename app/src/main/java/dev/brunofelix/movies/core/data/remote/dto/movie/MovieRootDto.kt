package dev.brunofelix.movies.core.data.remote.dto.movie

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object representing the root response for movie list requests.
 *
 * @property page The current page number of the results.
 * @property results List of [MovieDto] objects for the current page.
 * @property totalPages The total number of pages available.
 * @property totalResults The total number of results available.
 * @property dates Optional [MovieDateDto] representing the date range for the current list (e.g., for "Now Playing").
 */
data class MovieRootDto(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: List<MovieDto>?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?,

    @SerializedName("dates")
    var dates: MovieDateDto?,
)
