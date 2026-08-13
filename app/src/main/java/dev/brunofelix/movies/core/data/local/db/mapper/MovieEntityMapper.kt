package dev.brunofelix.movies.core.data.local.db.mapper

import dev.brunofelix.movies.core.data.local.db.entity.MovieEntity
import dev.brunofelix.movies.core.domain.model.Movie

/**
 * Maps a [MovieEntity] (Database model) to a [Movie] (Domain model).
 *
 * Used when reading movie data from the local database to be used in the domain layer.
 *
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
 * Maps a [Movie] (Domain model) to a [MovieEntity] (Database model).
 *
 * Used when saving a movie from the domain layer into the local database.
 *
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

