package dev.brunofelix.movies.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoRootDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("results")
    val results: List<VideoDto>?
)