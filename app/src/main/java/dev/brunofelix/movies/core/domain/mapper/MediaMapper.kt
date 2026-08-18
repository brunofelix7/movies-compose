package dev.brunofelix.movies.core.domain.mapper

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.model.enums.MediaType

fun Movie.toMedia() = Media(
    id = id,
    title = title,
    posterPath = posterPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    duration = duration,
    type = MediaType.MOVIE
)

fun List<Movie>.toMovieMediaList() = map { it.toMedia() }

fun TvShow.toMedia() = Media(
    id = id,
    title = name,
    posterPath = posterPath,
    voteAverage = voteAverage,
    releaseDate = firstAirDate,
    type = MediaType.TV_SHOW
)

fun List<TvShow>.toTvShowMediaList() = map { it.toMedia() }
