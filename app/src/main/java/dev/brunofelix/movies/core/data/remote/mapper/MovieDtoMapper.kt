package dev.brunofelix.movies.core.data.remote.mapper

import dev.brunofelix.movies.core.data.remote.dto.movie.MovieDto
import dev.brunofelix.movies.core.data.remote.dto.movie.MovieRootDto
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.util.extension.toBackdropUrl
import dev.brunofelix.movies.core.domain.util.extension.toPostUrl

/**
 * Extension function to map a [MovieRootDto] to a list of [Movie] domain models.
 * @return A list of [Movie]s or an empty list if results are null.
 */
fun MovieRootDto.toDomainList(): List<Movie> {
    return results?.map { it.toDomain() } ?: emptyList()
}

/**
 * Extension function to map a [MovieDto] to a [Movie] domain model.
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
