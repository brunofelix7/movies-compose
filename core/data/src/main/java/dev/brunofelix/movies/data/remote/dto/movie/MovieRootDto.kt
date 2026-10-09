package dev.brunofelix.movies.data.remote.dto.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing the root response for movie list requests.
 *
 * @property page The current page number of the results.
 * @property results List of [MovieDto] objects for the current page.
 * @property totalPages The total number of pages available.
 * @property totalResults The total number of results available.
 * @property dates Optional [MovieDateDto] representing the date range for the current list (e.g., for "Now Playing").
 */
@Serializable
data class MovieRootDto(
    @SerialName("page")
    val page: Int? = null,

    @SerialName("results")
    val results: List<MovieDto>? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("total_results")
    val totalResults: Int? = null,

    @SerialName("dates")
    val dates: MovieDateDto? = null
)
