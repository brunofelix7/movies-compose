package dev.brunofelix.movies.core.domain.util.datetime

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.Month
import java.time.ZoneId

class DateTimeConverterTest : DescribeSpec({

    context("DateTimeConverter") {
        describe("format with string input") {
            it("should convert yyyy-MM-dd to dd/MM/yyyy correctly") {
                val input = "2026-08-13"
                val result = DateTimeConverter.format(
                    value = input,
                    fromPattern = DateTimeConverter.YYYY_MM_DD,
                    toPattern = DateTimeConverter.DD_MM_YYYY
                )
                result.value shouldBe "13/08/2026"
                result.localDateTime.year shouldBe 2026
                result.localDateTime.month shouldBe Month.AUGUST
                result.localDateTime.dayOfMonth shouldBe 13
            }
            it("should handle different time zones correctly") {
                val input = "2026-08-13"
                val fromZone = ZoneId.of("UTC")
                val toZone = ZoneId.of("America/Sao_Paulo")

                val result = DateTimeConverter.format(
                    value = input,
                    fromPattern = DateTimeConverter.YYYY_MM_DD,
                    toPattern = DateTimeConverter.DD_MM_YYYY,
                    fromZone = fromZone,
                    toZone = toZone
                )

                // 2026-08-13 00:00 UTC is 2026-08-12 21:00 BRT
                result.value shouldBe "12/08/2026"
                result.localDateTime.dayOfMonth shouldBe 12
                result.localDateTime.hour shouldBe 21
            }
            it("should return default result when input string is invalid") {
                val input = "invalid-date"
                val result = DateTimeConverter.format(
                    value = input,
                    fromPattern = DateTimeConverter.YYYY_MM_DD,
                    toPattern = DateTimeConverter.DD_MM_YYYY
                )
                result.value shouldBe "--"
                result.timestamp shouldBe 0L
            }
        }

        describe("format with timestamp input") {
            it("should convert timestamp to formatted string correctly") {
                // 1723554000000L = 2024-08-13 13:00:00 UTC
                val timestamp = 1723554000000L
                val result = DateTimeConverter.format(
                    timestamp = timestamp,
                    toPattern = DateTimeConverter.DD_MM_YYYY,
                    toZone = ZoneId.of("UTC")
                )
                result.value shouldBe "13/08/2024"
                result.timestamp shouldBe timestamp
                result.localDateTime.year shouldBe 2024
                result.localDateTime.month shouldBe Month.AUGUST
                result.localDateTime.dayOfMonth shouldBe 13
            }
            it("should respect the target time zone for timestamps") {
                // 1723554000000L = 2024-08-13 13:00:00 UTC
                val timestamp = 1723554000000L
                val result = DateTimeConverter.format(
                    timestamp = timestamp,
                    toPattern = "dd/MM/yyyy HH:mm",
                    toZone = ZoneId.of("America/Sao_Paulo")
                )

                // UTC 13:00 -> BRT 10:00 (-3h)
                result.value shouldBe "13/08/2024 10:00"
            }
            it("should return default result on conversion failure") {
                // Testing with a negative value which we now handle as invalid
                val resultNegative = DateTimeConverter.format(
                    timestamp = -1L,
                    toPattern = DateTimeConverter.DD_MM_YYYY
                )
                resultNegative.value shouldBe "--"
                resultNegative.timestamp shouldBe 0L

                // Testing with an invalid pattern to trigger exception
                val resultInvalidPattern = DateTimeConverter.format(
                    timestamp = 1723554000000L,
                    toPattern = "invalid-pattern"
                )
                resultInvalidPattern.value shouldBe "--"
            }
        }
    }
})
