package dev.brunofelix.movies.data.local.mapper

import dev.brunofelix.movies.data.local.entity.MediaEntity
import dev.brunofelix.movies.domain.model.Media

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
