package dev.brunofelix.movies.core.presentation.ui.model

import dev.brunofelix.movies.core.domain.model.enums.MediaType

data class MediaUiModel(
    val id: Long = 0L,
    val title: String = "",
    val posterPath: String = "",
    val type: MediaType = MediaType.MOVIE
)