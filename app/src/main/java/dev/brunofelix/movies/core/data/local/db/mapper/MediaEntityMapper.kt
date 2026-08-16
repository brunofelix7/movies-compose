package dev.brunofelix.movies.core.data.local.db.mapper

import dev.brunofelix.movies.core.data.local.db.entity.MediaEntity
import dev.brunofelix.movies.core.domain.model.Media

/**
 * Maps a [MediaEntity] (Database model) to a [Media] (Domain model).
 *
 * Used when reading media data from the local database to be used in the domain layer.
 *
 * @return A domain representation of the media.
 */
fun MediaEntity.toDomain(): Media {
    return Media(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        duration = duration,
        releaseDate = releaseDate,
        type = type
    )
}

/**
 * Maps a [Media] (Domain model) to a [MediaEntity] (Database model).
 *
 * Used when saving a media from the domain layer into the local database.
 *
 * @return A database entity representation of the media.
 */
fun Media.toEntity(): MediaEntity {
    return MediaEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        duration = duration,
        releaseDate = releaseDate,
        type = type
    )
}

