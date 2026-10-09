package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.core.data.BuildConfig
import dev.brunofelix.movies.data.remote.dto.tv_show.TvShowDto
import dev.brunofelix.movies.data.remote.dto.tv_show.TvShowRootDto
import dev.brunofelix.movies.data.test_util.DtoFactory
import dev.brunofelix.movies.domain.model.MovieGenre
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class TvShowDtoMapperTest : DescribeSpec({

    describe("TvShowDto.toDomain") {
        it("should map the fields and build absolute image URLs") {
            val tvShow = DtoFactory.tvShowDto(id = 2L).toDomain()

            tvShow.id shouldBe 2L
            tvShow.name shouldBe "Show 2"
            tvShow.originalName shouldBe "Original 2"
            tvShow.originalLanguage shouldBe "en"
            tvShow.overview shouldBe "Overview 2"
            tvShow.posterPath shouldBe "${BuildConfig.BASE_URL_IMAGE}/poster2.jpg"
            tvShow.backdropPath shouldBe "${BuildConfig.BASE_URL_IMAGE}/backdrop2.jpg"
            tvShow.firstAirDate shouldBe "2023-05-02"
            tvShow.genreIds shouldBe listOf(18)
            tvShow.popularity shouldBe 12.5
            tvShow.voteAverage shouldBe 8.2f
            tvShow.voteCount shouldBe 50
            tvShow.genres shouldBe listOf(MovieGenre(id = 18, name = "Drama"))
            tvShow.homepage shouldBe "https://show.example"
            tvShow.originCountry shouldBe listOf("US")
            tvShow.status shouldBe "Ended"
            tvShow.tagline shouldBe "Tagline"
            tvShow.numberOfEpisodes shouldBe 20
            tvShow.numberOfSeasons shouldBe 2
            tvShow.seasons.map { it.seasonNumber } shouldBe listOf(1)
            tvShow.type shouldBe "Scripted"
        }

        it("should fall back to defaults when the fields are missing") {
            val tvShow = TvShowDto().toDomain()

            tvShow.id shouldBe -1L
            tvShow.name shouldBe ""
            tvShow.posterPath shouldBe ""
            tvShow.voteAverage shouldBe 0f
            tvShow.seasons.shouldBeEmpty()
        }
    }

    describe("TvShowRootDto.toDomainList") {
        it("should map every result") {
            TvShowRootDto(results = listOf(DtoFactory.tvShowDto(1L))).toDomainList().single().id shouldBe 1L
        }

        it("should return an empty list when there are no results") {
            TvShowRootDto().toDomainList().shouldBeEmpty()
        }
    }
})
