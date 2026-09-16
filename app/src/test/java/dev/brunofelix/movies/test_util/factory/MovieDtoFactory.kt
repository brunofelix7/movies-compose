package dev.brunofelix.movies.test_util.factory

import dev.brunofelix.movies.test_util.fake.FakeMovie
import dev.brunofelix.movies.core.data.remote.dto.movie.MovieDto

class MovieDtoFactory {

    fun create(movie: FakeMovie) = when (movie) {
        FakeMovie.JohnWick -> create(
            id = 1,
            title = "John Wick",
            voteAverage = 7.1F
        )
        FakeMovie.Avengers -> create(
            id = 2,
            title = "Avengers",
            voteAverage = 7.9F
        )
        FakeMovie.AlienRomulus -> create(
            id = 3,
            title = "Alien Romulus",
            voteAverage = 8.2F
        )
    }

    private fun create(
        id: Long,
        title: String,
        voteAverage: Float
    ) = MovieDto(
        id = id,
        title = title,
        originalTitle = title,
        originalLanguage = "en",
        overview = "",
        posterPath = "/{posterPath}",
        backdropPath = "/{backdropPath}",
        releaseDate = "2024-01-01",
        adult = false,
        genreIds = emptyList(),
        popularity = 0.0,
        video = false,
        voteAverage = voteAverage,
        voteCount = 0,
        budget = 0,
        genres = emptyList(),
        homepage = "",
        imdbId = "",
        originCountry = emptyList(),
        revenue = 0L,
        runtime = 0,
        status = "Released",
        tagline = ""
    )
}
