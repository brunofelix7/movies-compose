package dev.brunofelix.movies.core.presentation.ui.model

import dev.brunofelix.movies.core.domain.model.MovieGenre

data class MovieUiModel(
    val id: Long = 0L,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val voteAverage: String = "",
    val duration: String = "",
    val trailerKey: String? = null,
    val genres: List<MovieGenre> = emptyList()
)