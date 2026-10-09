package dev.brunofelix.movies.domain.util.datetime

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import java.time.ZoneId

class DateTimeConverterTest : DescribeSpec({

    val utc: ZoneId = ZoneId.of("UTC")

    describe("format from a date string") {
        it("should convert yyyy-MM-dd to dd/MM/yyyy") {
            val result = DateTimeConverter.format(
                value = "2024-08-13",
                fromPattern = DateTimeConverter.YYYY_MM_DD,
                toPattern = DateTimeConverter.DD_MM_YYYY,
                fromZone = utc,
                toZone = utc
            )

            result.value shouldBe "13/08/2024"
            result.localDateTime shouldBe LocalDateTime.of(2024, 8, 13, 0, 0)
        }

        it("should expose the epoch millis of the start of the day") {
            val result = DateTimeConverter.format(
                value = "1970-01-02",
                fromPattern = DateTimeConverter.YYYY_MM_DD,
                toPattern = DateTimeConverter.DD_MM_YYYY,
                fromZone = utc,
                toZone = utc
            )

            result.timestamp shouldBe 86_400_000L
        }

        it("should return the default placeholder when the value does not match the pattern") {
            val result = DateTimeConverter.format(
                value = "13/08/2024",
                fromPattern = DateTimeConverter.YYYY_MM_DD,
                toPattern = DateTimeConverter.DD_MM_YYYY
            )

            result.value shouldBe "--"
            result.timestamp shouldBe 0L
        }

        it("should return the default placeholder for an empty value") {
            val result = DateTimeConverter.format(
                value = "",
                fromPattern = DateTimeConverter.YYYY_MM_DD,
                toPattern = DateTimeConverter.DD_MM_YYYY
            )

            result.value shouldBe "--"
        }
    }

    describe("format from a timestamp") {
        it("should format the timestamp in the given zone") {
            val result = DateTimeConverter.format(
                timestamp = 86_400_000L,
                toPattern = DateTimeConverter.DD_MM_YYYY,
                toZone = utc
            )

            result.value shouldBe "02/01/1970"
            result.timestamp shouldBe 86_400_000L
        }

        it("should return the default placeholder for a non positive timestamp") {
            DateTimeConverter.format(timestamp = 0L, toPattern = DateTimeConverter.DD_MM_YYYY).value shouldBe "--"
            DateTimeConverter.format(timestamp = -1L, toPattern = DateTimeConverter.DD_MM_YYYY).value shouldBe "--"
        }
    }
})
