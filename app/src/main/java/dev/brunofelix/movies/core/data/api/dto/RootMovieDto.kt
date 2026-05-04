package dev.brunofelix.movies.core.data.api.dto

import com.google.gson.annotations.SerializedName

data class RootMovieDto(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: List<MovieDto>?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?,

    @SerializedName("dates")
    var dates: MovieDateDto? = null,
)