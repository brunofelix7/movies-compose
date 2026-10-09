package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.core.data.BuildConfig
import dev.brunofelix.movies.data.remote.dto.movie.MovieDto
import dev.brunofelix.movies.data.remote.dto.movie.MovieRootDto
import dev.brunofelix.movies.data.test_util.DtoFactory
import dev.brunofelix.movies.domain.model.MovieGenre
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class MovieDtoMapperTest : DescribeSpec({

    describe("MovieDto.toDomain") {
        it("should map the fields and build absolute image URLs") {
            val movie = DtoFactory.movieDto(id = 3L).toDomain()

            movie.id shouldBe 3L
            movie.title shouldBe "Movie 3"
            movie.overview shouldBe "Overview 3"
            movie.posterPath shouldBe "${BuildConfig.BASE_URL_IMAGE}/poster3.jpg"
            movie.backdropPath shouldBe "${BuildConfig.BASE_URL_IMAGE}/backdrop3.jpg"
            movie.releaseDate shouldBe "2024-01-03"
            movie.voteAverage shouldBe 7.5f
            movie.voteCount shouldBe 100
            movie.duration shouldBe 120
            movie.genres shouldBe listOf(MovieGenre(id = 28, name = "Action"))
        }

        it("should fall back to defaults when the fields are missing") {
            val movie = MovieDto().toDomain()

            movie.id shouldBe -1L
            movie.title shouldBe "Undefined"
            movie.posterPath shouldBe ""
            movie.backdropPath shouldBe ""
            movie.voteAverage shouldBe -1.0f
            movie.duration shouldBe 0
            movie.genres.shouldBeEmpty()
        }
    }

    describe("MovieRootDto.toDomainList") {
        it("should map every result") {
            val root = MovieRootDto(results = listOf(DtoFactory.movieDto(1L), DtoFactory.movieDto(2L)))

            root.toDomainList().map { it.id } shouldBe listOf(1L, 2L)
        }

        it("should return an empty list when there are no results") {
            MovieRootDto().toDomainList().shouldBeEmpty()
        }
    }
})
