package dev.brunofelix.movies.presentation.model

import dev.brunofelix.movies.domain.model.MovieGenre
import dev.brunofelix.movies.domain.model.WatchAvailability

/**
 * @property watchAvailability Where the movie can be watched, `null` when it could not be
 * loaded (the label is hidden instead of guessing).
 */
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
    val cast: List<CastUiModel> = emptyList(),
    val watchAvailability: WatchAvailability? = null
)