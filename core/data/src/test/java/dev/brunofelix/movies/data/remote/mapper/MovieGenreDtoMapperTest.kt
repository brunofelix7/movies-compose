package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.data.remote.dto.movie.MovieGenreDto
import dev.brunofelix.movies.domain.model.MovieGenre
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MovieGenreDtoMapperTest : DescribeSpec({

    describe("MovieGenreDto.toDomain") {
        it("should map the genre") {
            MovieGenreDto(id = 35, name = "Comedy").toDomain() shouldBe MovieGenre(id = 35, name = "Comedy")
        }

        it("should fall back to defaults when the fields are missing") {
            MovieGenreDto().toDomain() shouldBe MovieGenre(id = -1, name = "--")
        }
    }
})
