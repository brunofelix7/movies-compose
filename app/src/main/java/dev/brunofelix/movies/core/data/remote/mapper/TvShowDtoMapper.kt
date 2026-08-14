package dev.brunofelix.movies.core.data.remote.mapper

import dev.brunofelix.movies.core.data.remote.dto.tv_show.TvShowDto
import dev.brunofelix.movies.core.data.remote.dto.tv_show.TvShowRootDto
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.util.extension.toBackdropUrl
import dev.brunofelix.movies.core.domain.util.extension.toPostUrl

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
        posterPath = posterPath?.toPostUrl() ?: "",
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
        type = type.orEmpty()
    )
}
