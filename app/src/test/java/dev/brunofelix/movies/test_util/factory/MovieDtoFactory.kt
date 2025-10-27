package dev.brunofelix.movies.test_util.factory

import dev.brunofelix.movies.test_util.fake.FakeMovie
import dev.brunofelix.movies.core.data.api.dto.MovieResultDto

class MovieDtoFactory {

    fun create(movie: FakeMovie) = when (movie) {
        FakeMovie.JohnWick -> MovieResultDto(
            id = 1,
            title = "John Wick",
            voteAverage = 7.1F,
            posterPath = "/{posterPath}"
        )
        FakeMovie.Avengers -> MovieResultDto(
            id = 2,
            title = "Avengers",
            voteAverage = 7.9F,
            posterPath = "/{posterPath}"
        )
        FakeMovie.AlienRomulus -> MovieResultDto(
            id = 3,
            title = "Alien Romulus",
            voteAverage = 8.2F,
            posterPath = "/{posterPath}"
        )
    }
}