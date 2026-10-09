package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.data.remote.dto.VideoDto
import dev.brunofelix.movies.data.remote.dto.VideoRootDto
import dev.brunofelix.movies.data.test_util.DtoFactory
import dev.brunofelix.movies.domain.model.Video
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class VideoDtoMapperTest : DescribeSpec({

    describe("VideoDto.toDomain") {
        it("should map the video") {
            DtoFactory.videoDto(key = "xyz").toDomain() shouldBe Video(
                id = "video-xyz",
                key = "xyz",
                name = "Trailer",
                site = "YouTube",
                type = "Trailer",
                official = true
            )
        }

        it("should fall back to defaults when the fields are missing") {
            VideoDto().toDomain() shouldBe Video()
        }
    }

    describe("VideoRootDto.toDomainList") {
        it("should map every video") {
            VideoRootDto(results = listOf(DtoFactory.videoDto("a"), DtoFactory.videoDto("b")))
                .toDomainList().map { it.key } shouldBe listOf("a", "b")
        }

        it("should return an empty list for missing results") {
            VideoRootDto().toDomainList().shouldBeEmpty()
            (null as VideoRootDto?).toDomainList().shouldBeEmpty()
        }
    }
})
