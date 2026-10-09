package dev.brunofelix.movies.data.local.mapper

import dev.brunofelix.movies.data.local.entity.MediaEntity
import dev.brunofelix.movies.data.local.entity.WatchProviderEntity
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.WatchProvider

fun MediaEntity.toDomain(): Media {
    return Media(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        duration = duration,
        releaseDate = releaseDate,
        type = type,
        watchProviders = watchProviders?.map { it.toDomain() }
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
        type = type,
        watchProviders = watchProviders?.map { it.toEntity() }
    )
}

fun WatchProviderEntity.toDomain(): WatchProvider {
    return WatchProvider(
        id = id,
        name = name,
        logoPath = logoPath
    )
}

fun WatchProvider.toEntity(): WatchProviderEntity {
    return WatchProviderEntity(
        id = id,
        name = name,
        logoPath = logoPath
    )
}
