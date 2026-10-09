package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.data.remote.dto.tv_show.TvShowDto
import dev.brunofelix.movies.data.remote.dto.tv_show.TvShowRootDto
import dev.brunofelix.movies.data.util.extension.toBackdropUrl
import dev.brunofelix.movies.data.util.extension.toPosterUrl
import dev.brunofelix.movies.domain.model.TvShow

/**
 * Maps a [TvShowRootDto] (API response) to a list of [TvShow] domain models.
 *
 * @return A list of [TvShow] objects, or an empty list if results are null.
 */
fun TvShowRootDto.toDomainList(): List<TvShow> {
    return results?.map { it.toDomain() } ?: emptyList()
}

/**
 * Maps a [TvShowDto] (API data object) to a [TvShow] domain model.
 *
 * Provides safe defaults for all nullable fields received from the API.
 *
 * @return A [TvShow] domain model.
 */
fun TvShowDto.toDomain(): TvShow {
    return TvShow(
        id = id ?: -1L,
        name = name.orEmpty(),
        originalName = originalName.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath?.toPosterUrl() ?: "",
        backdropPath = backdropPath?.toBackdropUrl() ?: "",
        firstAirDate = firstAirDate.orEmpty(),
        genreIds = genreIds ?: emptyList(),
        popularity = popularity ?: 0.0,
        voteAverage = voteAverage ?: 0f,
        voteCount = voteCount ?: 0,
        genres = genres?.map { it.toDomain() } ?: emptyList(),
        homepage = homepage.orEmpty(),
        originCountry = originCountry ?: emptyList(),
        status = status.orEmpty(),
        tagline = tagline.orEmpty(),
        numberOfEpisodes = numberOfEpisodes ?: 0,
        numberOfSeasons = numberOfSeasons ?: 0,
        seasons = seasons?.map { it.toDomain() } ?: emptyList(),
        type = type.orEmpty()
    )
}
