package dev.brunofelix.movies.domain.util.extension

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.WatchAvailability
import dev.brunofelix.movies.domain.model.WatchProvider
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class WatchAvailabilityExtTest : DescribeSpec({

    val today = LocalDate.of(2026, 10, 9)
    val providers = listOf(WatchProvider(id = 8L, name = "Netflix"))

    describe("List<WatchProvider>.toWatchAvailability") {
        it("should be Streaming when there are providers") {
            providers.toWatchAvailability() shouldBe WatchAvailability.Streaming(providers)
        }

        it("should be Unavailable when there are no providers") {
            emptyList<WatchProvider>().toWatchAvailability() shouldBe WatchAvailability.Unavailable
        }
    }

    describe("Movie.toWatchAvailability") {
        it("should be Streaming when there are providers, even for a recent premiere") {
            Movie(releaseDate = "2026-10-01").toWatchAvailability(providers, today) shouldBe
                WatchAvailability.Streaming(providers)
        }

        it("should be InTheaters when it premiered today") {
            Movie(releaseDate = "2026-10-09").toWatchAvailability(emptyList(), today) shouldBe
                WatchAvailability.InTheaters
        }

        it("should be InTheaters on the last day of the theatrical window") {
            Movie(releaseDate = "2026-08-25").toWatchAvailability(emptyList(), today) shouldBe
                WatchAvailability.InTheaters
        }

        it("should be Unavailable once the theatrical window is over") {
            Movie(releaseDate = "2026-08-24").toWatchAvailability(emptyList(), today) shouldBe
                WatchAvailability.Unavailable
        }

        it("should be Unavailable before the premiere") {
            Movie(releaseDate = "2026-10-10").toWatchAvailability(emptyList(), today) shouldBe
                WatchAvailability.Unavailable
        }

        it("should be Unavailable when the release date is missing or malformed") {
            Movie(releaseDate = "").toWatchAvailability(emptyList(), today) shouldBe WatchAvailability.Unavailable
            Movie(releaseDate = "10/09/2026").toWatchAvailability(emptyList(), today) shouldBe
                WatchAvailability.Unavailable
        }
    }
})
