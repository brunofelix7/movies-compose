package dev.brunofelix.movies.core.presentation.state

import dev.brunofelix.movies.core.domain.model.MovieGenre

data class TvShowUiState(
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
