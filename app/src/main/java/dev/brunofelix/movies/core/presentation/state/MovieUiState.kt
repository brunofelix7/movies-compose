package dev.brunofelix.movies.core.presentation.state

import dev.brunofelix.movies.core.domain.model.MovieGenre

data class MovieUiState(
    val id: Long = 0L,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val voteAverage: Float = 0F,
    val duration: Int = 0,
    val genres: List<MovieGenre> = emptyList()
)
