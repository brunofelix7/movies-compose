package dev.brunofelix.movies.core.data.remote.mapper

import dev.brunofelix.movies.core.data.remote.dto.tv_show.TvShowDto
import dev.brunofelix.movies.core.domain.model.TvShow

fun TvShowDto.toDomain(): TvShow {
    return TvShow(
        id = id ?: 0L,
        name = name.orEmpty(),
        originalName = originalName.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
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
