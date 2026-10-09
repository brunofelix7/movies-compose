package dev.brunofelix.movies.domain.util.extension

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.enums.MediaType

fun Movie.toMedia(): Media = Media(
    id = id,
    title = title,
    posterPath = posterPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    duration = duration,
    type = MediaType.MOVIE
)

fun List<Movie>.toMovieMediaList(): List<Media> = map { it.toMedia() }

fun TvShow.toMedia(): Media = Media(
    id = id,
    title = name,
    posterPath = posterPath,
    voteAverage = voteAverage,
    releaseDate = firstAirDate,
    type = MediaType.TV_SHOW
)

fun List<TvShow>.toTvShowMediaList(): List<Media> = map { it.toMedia() }
