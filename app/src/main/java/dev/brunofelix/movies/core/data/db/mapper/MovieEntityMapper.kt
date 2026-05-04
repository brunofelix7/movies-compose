package dev.brunofelix.movies.core.data.db.mapper

import dev.brunofelix.movies.core.data.db.entity.MovieEntity
import dev.brunofelix.movies.core.domain.model.Movie

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        duration = duration,
        releaseDate = releaseDate
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        duration = duration,
        releaseDate = releaseDate
    )
}