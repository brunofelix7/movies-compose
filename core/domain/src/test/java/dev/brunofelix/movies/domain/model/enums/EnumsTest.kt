package dev.brunofelix.movies.domain.model.enums

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class EnumsTest : DescribeSpec({

    describe("LanguageEnum.fromCode") {
        it("should find the language by its code") {
            LanguageEnum.fromCode("pt-BR") shouldBe LanguageEnum.PORTUGUESE
            LanguageEnum.fromCode("es") shouldBe LanguageEnum.SPANISH
        }

        it("should fall back to English for an unknown or missing code") {
            LanguageEnum.fromCode("fr") shouldBe LanguageEnum.ENGLISH
            LanguageEnum.fromCode(null) shouldBe LanguageEnum.ENGLISH
        }
    }

    describe("MediaListCategory.mediaType") {
        it("should map movie lists to MOVIE") {
            listOf(
                MediaListCategory.MOVIE_POPULAR,
                MediaListCategory.MOVIE_UPCOMING,
                MediaListCategory.MOVIE_TOP_RATED,
                MediaListCategory.RELEASE_THEATERS,
                MediaListCategory.RELEASE_STREAMING
            ).map { it.mediaType }.distinct() shouldBe listOf(MediaType.MOVIE)
        }

        it("should map TV show lists to TV_SHOW") {
            listOf(
                MediaListCategory.TV_SHOW_POPULAR,
                MediaListCategory.TV_SHOW_TOP_RATED,
                MediaListCategory.RELEASE_SERIES
            ).map { it.mediaType }.distinct() shouldBe listOf(MediaType.TV_SHOW)
        }
    }
})
