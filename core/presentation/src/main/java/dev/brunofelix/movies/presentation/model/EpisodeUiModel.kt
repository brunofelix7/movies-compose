package dev.brunofelix.movies.presentation.model

data class EpisodeUiModel(
    val id: Long = 0L,
    val name: String = "",
    val overview: String = "",
    val stillPath: String = "",
    val episodeNumber: Int = 0,
    val runtime: String = "",
    val airDate: String = ""
)
