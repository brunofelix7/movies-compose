package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.core.data.BuildConfig
import dev.brunofelix.movies.data.remote.dto.CastDto
import dev.brunofelix.movies.data.remote.dto.CreditsRootDto
import dev.brunofelix.movies.data.test_util.DtoFactory
import dev.brunofelix.movies.domain.model.Cast
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class CastDtoMapperTest : DescribeSpec({

    describe("CastDto.toDomain") {
        it("should map the fields and build the profile URL") {
            DtoFactory.castDto(id = 4L).toDomain() shouldBe Cast(
                id = 4L,
                name = "Actor 4",
                character = "Character 4",
                profilePath = "${BuildConfig.BASE_URL_IMAGE}/profile4.jpg"
            )
        }

        it("should fall back to defaults when the fields are missing") {
            CastDto().toDomain() shouldBe Cast(id = -1L)
        }
    }

    describe("CreditsRootDto.toCastList") {
        it("should sort the cast by billing order, unknown orders last") {
            val credits = CreditsRootDto(
                cast = listOf(
                    DtoFactory.castDto(id = 1L, order = null),
                    DtoFactory.castDto(id = 2L, order = 5),
                    DtoFactory.castDto(id = 3L, order = 0)
                )
            )

            credits.toCastList().map { it.id } shouldBe listOf(3L, 2L, 1L)
        }

        it("should return an empty list for missing credits") {
            CreditsRootDto().toCastList().shouldBeEmpty()
            (null as CreditsRootDto?).toCastList().shouldBeEmpty()
        }
    }
})
