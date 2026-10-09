package dev.brunofelix.movies.data.local.mapper

import dev.brunofelix.movies.data.local.entity.MediaEntity
import dev.brunofelix.movies.data.local.entity.WatchProviderEntity
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.model.enums.MediaType
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class MediaEntityMapperTest : DescribeSpec({

    val media = Media(
        id = 5L,
        title = "Dark",
        posterPath = "https://image/dark.jpg",
        voteAverage = 8.7f,
        releaseDate = "2017-12-01",
        duration = 60,
        type = MediaType.TV_SHOW,
        watchProviders = listOf(WatchProvider(id = 8L, name = "Netflix", logoPath = "https://image/netflix.jpg"))
    )

    val entity = MediaEntity(
        id = 5L,
        title = "Dark",
        posterPath = "https://image/dark.jpg",
        voteAverage = 8.7f,
        duration = 60,
        releaseDate = "2017-12-01",
        type = MediaType.TV_SHOW,
        watchProviders = listOf(WatchProviderEntity(id = 8L, name = "Netflix", logoPath = "https://image/netflix.jpg"))
    )

    describe("MediaEntity.toDomain") {
        it("should map every column to the domain model") {
            entity.toDomain() shouldBe media
        }

        it("should keep providers that were never fetched as null") {
            entity.copy(watchProviders = null).toDomain().watchProviders.shouldBeNull()
        }
    }

    describe("Media.toEntity") {
        it("should map every field to the entity") {
            media.toEntity() shouldBe entity
        }

        it("should round trip without losing data") {
            media.toEntity().toDomain() shouldBe media
        }

        it("should keep an empty provider list apart from unknown providers") {
            media.copy(watchProviders = emptyList()).toEntity().watchProviders shouldBe emptyList()
            media.copy(watchProviders = null).toEntity().watchProviders.shouldBeNull()
        }
    }
})
