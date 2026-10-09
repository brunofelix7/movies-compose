package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.data.remote.dto.movie.MovieDateDto
import dev.brunofelix.movies.domain.model.MovieDate
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MovieDateDtoMapperTest : DescribeSpec({

    describe("MovieDateDto.toDomain") {
        it("should map the date range") {
            MovieDateDto(maximum = "2024-02-01", minimum = "2024-01-01").toDomain() shouldBe
                MovieDate(maximum = "2024-02-01", minimum = "2024-01-01")
        }

        it("should fall back to empty strings when the fields are missing") {
            MovieDateDto().toDomain() shouldBe MovieDate()
        }
    }
})
