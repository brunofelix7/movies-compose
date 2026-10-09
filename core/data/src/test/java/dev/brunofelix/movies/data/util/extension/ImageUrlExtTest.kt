package dev.brunofelix.movies.data.util.extension

import dev.brunofelix.movies.core.data.BuildConfig
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ImageUrlExtTest : DescribeSpec({

    val path = "/image.jpg"
    val expected = "${BuildConfig.BASE_URL_IMAGE}$path"

    describe("image URL extensions") {
        it("should prefix a poster path with the image base URL") {
            path.toPosterUrl() shouldBe expected
        }

        it("should prefix a backdrop path with the image base URL") {
            path.toBackdropUrl() shouldBe expected
        }

        it("should prefix a still path with the image base URL") {
            path.toStillUrl() shouldBe expected
        }

        it("should prefix a profile path with the image base URL") {
            path.toProfileUrl() shouldBe expected
        }

        it("should prefix a logo path with the image base URL") {
            path.toLogoUrl() shouldBe expected
        }
    }
})
