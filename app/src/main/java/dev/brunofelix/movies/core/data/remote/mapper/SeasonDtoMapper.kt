package dev.brunofelix.movies.core.data.remote.mapper

import dev.brunofelix.movies.core.data.remote.dto.tv_show.EpisodeDto
import dev.brunofelix.movies.core.data.remote.dto.tv_show.SeasonDto
import dev.brunofelix.movies.core.domain.model.Episode
import dev.brunofelix.movies.core.domain.model.Season
import dev.brunofelix.movies.core.domain.util.extension.toPostUrl
import dev.brunofelix.movies.core.domain.util.extension.toStillUrl

/**
 * Maps a [SeasonDto] (API data object) to a [Season] domain model.
 *
 * Provides safe defaults for all nullable fields received from the API.
 *
 * @return A [Season] domain model.
 */
fun SeasonDto.toDomain(): Season {
    return Season(
        id = id ?: -1L,
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath?.toPostUrl() ?: "",
        seasonNumber = seasonNumber ?: 0,
        episodeCount = episodeCount ?: 0,
        airDate = airDate.orEmpty(),
        voteAverage = voteAverage ?: 0f
    )
}

/**
 * Maps the `episodes` array of a season response to a list of [Episode] domain models.
 *
 * @return A list of [Episode] objects, or an empty list if the season carries no episodes.
 */
fun SeasonDto.toEpisodeList(): List<Episode> {
    return episodes?.map { it.toDomain() } ?: emptyList()
}

/**
 * Maps an [EpisodeDto] (API data object) to an [Episode] domain model.
 *
 * @return An [Episode] domain model.
 */
fun EpisodeDto.toDomain(): Episode {
    return Episode(
        id = id ?: -1L,
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        stillPath = stillPath?.toStillUrl() ?: "",
        episodeNumber = episodeNumber ?: 0,
        seasonNumber = seasonNumber ?: 0,
        runtime = runtime ?: 0,
        airDate = airDate.orEmpty(),
        voteAverage = voteAverage ?: 0f
    )
}
