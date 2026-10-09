package dev.brunofelix.movies.presentation.mapper

import dev.brunofelix.movies.domain.model.Video
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class VideoMapperTest : DescribeSpec({

    describe("toTrailerKey") {
        it("should prefer a YouTube trailer") {
            listOf(
                Video(key = "teaser", site = "YouTube", type = "Teaser"),
                Video(key = "vimeo", site = "Vimeo", type = "Trailer"),
                Video(key = "trailer", site = "youtube", type = "trailer")
            ).toTrailerKey() shouldBe "trailer"
        }

        it("should fall back to any YouTube video") {
            listOf(
                Video(key = "vimeo", site = "Vimeo", type = "Trailer"),
                Video(key = "clip", site = "YouTube", type = "Clip")
            ).toTrailerKey() shouldBe "clip"
        }

        it("should be null when nothing can be played") {
            listOf(Video(key = "vimeo", site = "Vimeo")).toTrailerKey().shouldBeNull()
            emptyList<Video>().toTrailerKey().shouldBeNull()
        }
    }
})
