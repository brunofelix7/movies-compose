package dev.brunofelix.movies.core.data.local.db.mapper

import dev.brunofelix.movies.core.data.local.db.entity.MovieEntity
import dev.brunofelix.movies.core.domain.model.Movie

/**
 * Extension function to map a [MovieEntity] (database model) to a [Movie] (domain model).
 * @return A domain representation of the movie.
 */
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

/**
 * Extension function to map a [Movie] (domain model) to a [MovieEntity] (database model).
 * @return A database entity representation of the movie.
 */
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

