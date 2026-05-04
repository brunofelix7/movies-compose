package dev.brunofelix.movies.test_util.factory

import dev.brunofelix.movies.test_util.fake.FakeMovie
import dev.brunofelix.movies.core.data.api.dto.MovieDto

class MovieDtoFactory {

    fun create(movie: FakeMovie) = when (movie) {
        FakeMovie.JohnWick -> MovieDto(
            id = 1,
            title = "John Wick",
            voteAverage = 7.1F,
            posterPath = "/{posterPath}"
        )
        FakeMovie.Avengers -> MovieDto(
            id = 2,
            title = "Avengers",
            voteAverage = 7.9F,
            posterPath = "/{posterPath}"
        )
        FakeMovie.AlienRomulus -> MovieDto(
            id = 3,
            title = "Alien Romulus",
            voteAverage = 8.2F,
            posterPath = "/{posterPath}"
        )
    }
}