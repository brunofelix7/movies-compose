package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.core.data.BuildConfig
import dev.brunofelix.movies.data.remote.dto.tv_show.EpisodeDto
import dev.brunofelix.movies.data.remote.dto.tv_show.SeasonDto
import dev.brunofelix.movies.data.test_util.DtoFactory
import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.model.Season
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class SeasonDtoMapperTest : DescribeSpec({

    describe("SeasonDto.toDomain") {
        it("should map the fields and build the poster URL") {
            DtoFactory.seasonDto(number = 2).toDomain() shouldBe Season(
                id = 12L,
                name = "Season 2",
                overview = "Season overview",
                posterPath = "${BuildConfig.BASE_URL_IMAGE}/season2.jpg",
                seasonNumber = 2,
                episodeCount = 10,
                airDate = "2023-05-01",
                voteAverage = 7.9f
            )
        }

        it("should fall back to defaults when the fields are missing") {
            SeasonDto().toDomain() shouldBe Season(id = -1L)
        }
    }

    describe("SeasonDto.toEpisodeList") {
        it("should map every episode") {
            DtoFactory.seasonDto().toEpisodeList().map { it.id } shouldBe listOf(101L)
        }

        it("should return an empty list when there are no episodes") {
            SeasonDto().toEpisodeList().shouldBeEmpty()
        }
    }

    describe("EpisodeDto.toDomain") {
        it("should map the fields and build the still URL") {
            DtoFactory.episodeDto(number = 3).toDomain() shouldBe Episode(
                id = 103L,
                name = "Episode 3",
                overview = "Episode overview",
                stillPath = "${BuildConfig.BASE_URL_IMAGE}/still3.jpg",
                episodeNumber = 3,
                seasonNumber = 1,
                runtime = 45,
                airDate = "2023-05-01",
                voteAverage = 8.0f
            )
        }

        it("should fall back to defaults when the fields are missing") {
            EpisodeDto().toDomain() shouldBe Episode(id = -1L)
        }
    }
})
