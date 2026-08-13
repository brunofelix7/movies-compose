package dev.brunofelix.movies.core.data.remote.mapper

import dev.brunofelix.movies.core.data.remote.dto.movie.MovieDto
import dev.brunofelix.movies.core.data.remote.dto.movie.MovieRootDto
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.util.extension.toBackdropUrl
import dev.brunofelix.movies.core.domain.util.extension.toPostUrl

/**
 * Maps a [MovieRootDto] (API response) to a list of [Movie] domain models.
 *
 * This function extracts the `results` from the root DTO and converts each [MovieDto]
 * into a domain [Movie] object.
 *
 * @return A list of [Movie]s, or an empty list if results are null.
 */
fun MovieRootDto.toDomainList(): List<Movie> {
    return results?.map { it.toDomain() } ?: emptyList()
}

/**
 * Maps a [MovieDto] (API data object) to a [Movie] domain model.
 *
 * Handles null values by providing default empty strings or numeric fallbacks
 * and applies URL formatting for poster and backdrop paths.
 *
 * @return A domain representation of the movie.
 */
fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id ?: -1L,
        title = title ?: "Undefined",
        posterPath = posterPath?.toPostUrl() ?: "",
        voteAverage = voteAverage ?: -1.0F,
        genres = genres?.map { it.toDomain() } ?: emptyList(),
        overview = overview ?: "",
        backdropPath = backdropPath?.toBackdropUrl() ?: "",
        releaseDate = releaseDate ?: "",
        duration = runtime ?: 0,
        voteCount = voteCount ?: 0
    )
}
