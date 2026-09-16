package dev.brunofelix.movies.core.presentation.ui.model

data class SeasonUiModel(
    val id: Long = 0L,
    val name: String = "",
    val seasonNumber: Int = 0,
    val episodeCount: Int = 0,
    val airYear: String = ""
)
