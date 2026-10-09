package dev.brunofelix.movies.presentation.model

import dev.brunofelix.movies.domain.model.MovieGenre

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
    val genres: List<MovieGenre> = emptyList(),
    val cast: List<CastUiModel> = emptyList()
)