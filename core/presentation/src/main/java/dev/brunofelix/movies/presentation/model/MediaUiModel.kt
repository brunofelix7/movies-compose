package dev.brunofelix.movies.presentation.model

import dev.brunofelix.movies.domain.model.enums.MediaType

data class MediaUiModel(
    val id: Long = 0L,
    val title: String = "",
    val posterPath: String = "",
    val releaseDate: String = "",
    val voteAverage: String = "",
    val duration: String = "",
    val type: MediaType = MediaType.MOVIE
)