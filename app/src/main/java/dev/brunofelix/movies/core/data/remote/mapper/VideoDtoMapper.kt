package dev.brunofelix.movies.core.data.remote.mapper

import dev.brunofelix.movies.core.data.remote.dto.VideoDto
import dev.brunofelix.movies.core.data.remote.dto.VideoRootDto
import dev.brunofelix.movies.core.domain.model.Video

/**
 * Maps a [VideoRootDto] (API response) to a list of [Video] domain models.
 * @return A list of [Video] objects, or an empty list if results are null.
 */
fun VideoRootDto?.toDomainList(): List<Video> {
    return this?.results?.map { it.toDomain() } ?: emptyList()
}

/**
 * Maps a [VideoDto] (API data object) to a [Video] domain model.
 * @return A [Video] domain model.
 */
fun VideoDto.toDomain(): Video {
    return Video(
        id = id.orEmpty(),
        key = key.orEmpty(),
        name = name.orEmpty(),
        site = site.orEmpty(),
        type = type.orEmpty(),
        official = official ?: false
    )
}
