package dev.brunofelix.movies.core.presentation.mapper

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.util.datetime.DateTimeConverter
import dev.brunofelix.movies.core.domain.util.extension.formatDecimal
import dev.brunofelix.movies.core.presentation.ui.model.MediaUiModel
import dev.brunofelix.movies.core.presentation.ui.model.MovieUiModel

/**
 * Maps a [Media] domain model to a [MovieUiModel] for the presentation layer.
 *
 * @return A [MovieUiModel] containing formatted data for the UI.
 */
fun Media.toUiModel() = MediaUiModel(
    id = id,
    title = title,
    posterPath = posterPath,
    releaseDate = DateTimeConverter.format(
        value = releaseDate,
        fromPattern = DateTimeConverter.YYYY_MM_DD,
        toPattern = DateTimeConverter.DD_MM_YYYY
    ).value,
    voteAverage = if (voteAverage <= 0) "--" else voteAverage.formatDecimal(),
    duration = "${if (duration <= 0) "--" else duration}min",
    type = type
)