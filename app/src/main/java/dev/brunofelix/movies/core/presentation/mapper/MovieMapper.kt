package dev.brunofelix.movies.core.presentation.mapper

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.util.datetime.DateTimeConverter
import dev.brunofelix.movies.core.presentation.state.MovieUiState

/**
 * Maps a [Movie] domain model to a [MovieUiState] for the presentation layer.
 *
 * This function performs UI-specific transformations, such as converting the
 * release date from "YYYY-MM-DD" to "DD/MM/YYYY" format for display.
 *
 * @return A [MovieUiState] containing formatted data for the UI.
 */
fun Movie.toUiState(): MovieUiState {
    return MovieUiState(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = DateTimeConverter.format(
            value = releaseDate,
            fromPattern = DateTimeConverter.YYYY_MM_DD,
            toPattern = DateTimeConverter.DD_MM_YYYY
        ).value,
        voteAverage = voteAverage,
        duration = duration,
        genres = genres
    )
}
