package dev.brunofelix.movies.core.data.remote.dto.tv_show

import com.google.gson.annotations.SerializedName

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
