package dev.brunofelix.movies.feature.movie.domain.model

data class MovieDetails(
    val genres: List<Genre>? = emptyList(),
    val overview: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val duration: Int = 0,
    val voteCount: Int = 0,
)