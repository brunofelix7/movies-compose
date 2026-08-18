package dev.brunofelix.movies.core.presentation.mapper

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.util.datetime.DateTimeConverter
import dev.brunofelix.movies.core.domain.util.extension.formatDecimal
import dev.brunofelix.movies.core.presentation.ui.model.MovieUiModel

/**
 * Maps a [Movie] domain model to a [MovieUiModel] for the presentation layer.
 *
 * This function performs UI-specific transformations, such as converting the
 * release date from "YYYY-MM-DD" to "DD/MM/YYYY" format for display.
 *
 * @return A [MovieUiModel] containing formatted data for the UI.
 */
fun Movie.toUiModel(): MovieUiModel {
    return MovieUiModel(
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
        voteAverage = if (voteAverage <= 0) "--" else voteAverage.formatDecimal(),
        duration = "${if (duration <= 0) "--" else duration}min",
        genres = genres
    )
}
