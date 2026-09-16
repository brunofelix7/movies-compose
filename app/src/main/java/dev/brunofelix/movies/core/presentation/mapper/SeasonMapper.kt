package dev.brunofelix.movies.core.presentation.mapper

import dev.brunofelix.movies.core.domain.model.Episode
import dev.brunofelix.movies.core.domain.model.Season
import dev.brunofelix.movies.core.domain.util.datetime.DateTimeConverter
import dev.brunofelix.movies.core.presentation.ui.model.EpisodeUiModel
import dev.brunofelix.movies.core.presentation.ui.model.SeasonUiModel

private const val YYYY = "yyyy"

/**
 * Maps a [Season] domain model to a [SeasonUiModel], keeping only the air year since the
 * season header has room for little more than the name and the episode count.
 *
 * @return A [SeasonUiModel] containing formatted data for the UI.
 */
fun Season.toUiModel(): SeasonUiModel {
    return SeasonUiModel(
        id = id,
        name = name,
        seasonNumber = seasonNumber,
        episodeCount = episodeCount,
        airYear = DateTimeConverter.format(
            value = airDate,
            fromPattern = DateTimeConverter.YYYY_MM_DD,
            toPattern = YYYY
        ).value
    )
}

/**
 * Maps an [Episode] domain model to an [EpisodeUiModel] for the presentation layer.
 *
 * @return An [EpisodeUiModel] containing formatted data for the UI.
 */
fun Episode.toUiModel(): EpisodeUiModel {
    return EpisodeUiModel(
        id = id,
        name = name,
        overview = overview,
        stillPath = stillPath,
        episodeNumber = episodeNumber,
        runtime = "${if (runtime <= 0) "--" else runtime}min",
        airDate = DateTimeConverter.format(
            value = airDate,
            fromPattern = DateTimeConverter.YYYY_MM_DD,
            toPattern = DateTimeConverter.DD_MM_YYYY
        ).value
    )
}
