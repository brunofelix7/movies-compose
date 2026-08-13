package dev.brunofelix.movies.core.data.remote.dto

import com.google.gson.annotations.SerializedName

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
