package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.core.data.BuildConfig
import dev.brunofelix.movies.data.remote.dto.WatchProviderDto
import dev.brunofelix.movies.data.remote.dto.WatchProviderRegionDto
import dev.brunofelix.movies.data.remote.dto.WatchProvidersRootDto
import dev.brunofelix.movies.data.test_util.DtoFactory
import dev.brunofelix.movies.domain.model.WatchProvider
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class WatchProviderDtoMapperTest : DescribeSpec({

    describe("WatchProviderDto.toDomain") {
        it("should map the fields and build the logo URL") {
            DtoFactory.watchProviderDto(id = 8L).toDomain() shouldBe WatchProvider(
                id = 8L,
                name = "Provider 8",
                logoPath = "${BuildConfig.BASE_URL_IMAGE}/logo8.jpg"
            )
        }

        it("should fall back to defaults when the fields are missing") {
            WatchProviderDto().toDomain() shouldBe WatchProvider(id = -1L)
        }
    }

    describe("WatchProvidersRootDto.toWatchProviderList") {
        it("should keep only the offers of the requested region") {
            val root = WatchProvidersRootDto(
                results = mapOf(
                    "BR" to WatchProviderRegionDto(flatrate = listOf(DtoFactory.watchProviderDto(id = 8L))),
                    "US" to WatchProviderRegionDto(flatrate = listOf(DtoFactory.watchProviderDto(id = 9L)))
                )
            )

            root.toWatchProviderList("BR").map { it.id } shouldBe listOf(8L)
        }

        it("should merge the subscription, free and ads offers sorted by display priority") {
            val root = WatchProvidersRootDto(
                results = mapOf(
                    "BR" to WatchProviderRegionDto(
                        flatrate = listOf(DtoFactory.watchProviderDto(id = 1L, priority = 3)),
                        free = listOf(DtoFactory.watchProviderDto(id = 2L, priority = null)),
                        ads = listOf(DtoFactory.watchProviderDto(id = 3L, priority = 1))
                    )
                )
            )

            root.toWatchProviderList("BR").map { it.id } shouldBe listOf(3L, 1L, 2L)
        }

        it("should list a provider once when it is in more than one group") {
            val provider = DtoFactory.watchProviderDto(id = 8L)
            val root = WatchProvidersRootDto(
                results = mapOf("BR" to WatchProviderRegionDto(flatrate = listOf(provider), ads = listOf(provider)))
            )

            root.toWatchProviderList("BR").map { it.id } shouldBe listOf(8L)
        }

        it("should drop providers without an ID") {
            val root = WatchProvidersRootDto(
                results = mapOf("BR" to WatchProviderRegionDto(flatrate = listOf(WatchProviderDto(providerName = "?"))))
            )

            root.toWatchProviderList("BR").shouldBeEmpty()
        }

        it("should return an empty list when the region or the results are missing") {
            WatchProvidersRootDto(results = emptyMap()).toWatchProviderList("BR").shouldBeEmpty()
            WatchProvidersRootDto().toWatchProviderList("BR").shouldBeEmpty()
            (null as WatchProvidersRootDto?).toWatchProviderList("BR").shouldBeEmpty()
        }
    }
})
