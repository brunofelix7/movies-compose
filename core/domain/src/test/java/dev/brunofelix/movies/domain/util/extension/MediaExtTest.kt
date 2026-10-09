package dev.brunofelix.movies.domain.util.extension

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.enums.MediaType
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MediaExtTest : DescribeSpec({

    val movie = Movie(
        id = 7L,
        title = "Dune",
        posterPath = "https://image/dune.jpg",
        voteAverage = 8.1f,
        releaseDate = "2021-10-22",
        duration = 155
    )

    val tvShow = TvShow(
        id = 9L,
        name = "Dark",
        posterPath = "https://image/dark.jpg",
        voteAverage = 8.7f,
        firstAirDate = "2017-12-01"
    )

    describe("Movie.toMedia") {
        it("should map the movie fields and mark it as a movie") {
            movie.toMedia() shouldBe Media(
                id = 7L,
                title = "Dune",
                posterPath = "https://image/dune.jpg",
                voteAverage = 8.1f,
                releaseDate = "2021-10-22",
                duration = 155,
                type = MediaType.MOVIE
            )
        }

        it("should map every movie in a list") {
            listOf(movie, movie.copy(id = 8L)).toMovieMediaList().map { it.id } shouldBe listOf(7L, 8L)
        }
    }

    describe("TvShow.toMedia") {
        it("should map the TV show fields and mark it as a TV show") {
            tvShow.toMedia() shouldBe Media(
                id = 9L,
                title = "Dark",
                posterPath = "https://image/dark.jpg",
                voteAverage = 8.7f,
                releaseDate = "2017-12-01",
                type = MediaType.TV_SHOW
            )
        }

        it("should map every TV show in a list") {
            listOf(tvShow).toTvShowMediaList().single().type shouldBe MediaType.TV_SHOW
        }
    }
})
