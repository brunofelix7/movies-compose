package dev.brunofelix.movies.presentation.model

import dev.brunofelix.movies.domain.model.MovieGenre
import dev.brunofelix.movies.domain.model.WatchAvailability

/**
 * @property watchAvailability Where the TV show can be watched, `null` when it could not be
 * loaded (the label is hidden instead of guessing).
 */
data class TvShowUiModel(
    val id: Long = 0L,
    val name: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val firstAirDate: String = "",
    val voteAverage: String = "",
    val genres: List<MovieGenre> = emptyList(),
    val numberOfEpisodes: Int = 0,
    val numberOfSeasons: Int = 0,
    val seasons: List<SeasonUiModel> = emptyList(),
    val trailerKey: String? = null,
    val cast: List<CastUiModel> = emptyList(),
    val watchAvailability: WatchAvailability? = null
)
