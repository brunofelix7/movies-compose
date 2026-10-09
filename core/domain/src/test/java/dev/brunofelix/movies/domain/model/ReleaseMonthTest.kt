package dev.brunofelix.movies.domain.model

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.time.YearMonth
import java.util.Locale

class ReleaseMonthTest : DescribeSpec({

    describe("id and date bounds") {
        it("should expose the yyyy-MM id") {
            ReleaseMonth(year = 2026, month = 3).id shouldBe "2026-03"
        }

        it("should expose the first and last day of the month") {
            val february = ReleaseMonth(year = 2024, month = 2)

            february.startDate shouldBe "2024-02-01"
            february.endDate shouldBe "2024-02-29"
        }
    }

    describe("from") {
        it("should parse a valid id") {
            ReleaseMonth.from("2025-11") shouldBe ReleaseMonth(year = 2025, month = 11)
        }

        it("should return null for an invalid or missing id") {
            ReleaseMonth.from("11/2025").shouldBeNull()
            ReleaseMonth.from(null).shouldBeNull()
        }
    }

    describe("current") {
        it("should match the current year and month") {
            val now = YearMonth.now()

            ReleaseMonth.current() shouldBe ReleaseMonth(year = now.year, month = now.monthValue)
        }
    }

    describe("window") {
        it("should list the months around the current one in order") {
            val now = YearMonth.now()

            val window = ReleaseMonth.window(monthsBack = 1, monthsForward = 2)

            window shouldHaveSize 4
            window.first().id shouldBe now.minusMonths(1).toString()
            window[1] shouldBe ReleaseMonth.current()
            window.last().id shouldBe now.plusMonths(2).toString()
        }
    }

    describe("label") {
        it("should omit the year for the current year") {
            val month = ReleaseMonth(year = YearMonth.now().year, month = 1)

            month.label(Locale.US) shouldBe "Jan"
        }

        it("should append the year for any other year") {
            ReleaseMonth(year = 2001, month = 12).label(Locale.US) shouldBe "Dec 2001"
        }
    }
})
