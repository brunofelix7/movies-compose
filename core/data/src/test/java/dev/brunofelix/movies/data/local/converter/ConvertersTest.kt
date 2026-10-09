package dev.brunofelix.movies.data.local.converter

import dev.brunofelix.movies.data.local.entity.WatchProviderEntity
import dev.brunofelix.movies.domain.model.enums.MediaType
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class ConvertersTest : DescribeSpec({

    val converters = Converters()
    val providers = listOf(
        WatchProviderEntity(id = 8L, name = "Netflix", logoPath = "https://image/netflix.jpg"),
        WatchProviderEntity(id = 119L, name = "Prime Video", logoPath = "https://image/prime.jpg")
    )

    describe("media type") {
        it("should store the enum by name and read it back") {
            converters.fromMediaType(MediaType.TV_SHOW) shouldBe "TV_SHOW"
            converters.toMediaType("TV_SHOW") shouldBe MediaType.TV_SHOW
        }
    }

    describe("watch providers") {
        it("should round trip the providers through JSON") {
            converters.toWatchProviders(converters.fromWatchProviders(providers)) shouldBe providers
        }

        it("should store an empty list as an empty JSON array") {
            converters.fromWatchProviders(emptyList()) shouldBe "[]"
            converters.toWatchProviders("[]") shouldBe emptyList()
        }

        it("should keep null as null") {
            converters.fromWatchProviders(null).shouldBeNull()
            converters.toWatchProviders(null).shouldBeNull()
        }

        it("should read a malformed value as null") {
            converters.toWatchProviders("not json").shouldBeNull()
        }
    }
})
