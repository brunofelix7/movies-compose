package dev.brunofelix.movies.core.presentation.ui.model

import dev.brunofelix.movies.core.domain.model.MovieGenre

data class TvShowUiModel(
    val id: Long = 0L,
    val name: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val firstAirDate: String = "",
    val voteAverage: Float = 0F,
    val genres: List<MovieGenre> = emptyList(),
    val numberOfEpisodes: Int = 0,
    val numberOfSeasons: Int = 0
)