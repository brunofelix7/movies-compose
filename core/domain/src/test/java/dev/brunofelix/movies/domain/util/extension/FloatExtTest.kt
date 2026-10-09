package dev.brunofelix.movies.domain.util.extension

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.Locale

class FloatExtTest : DescribeSpec({

    val defaultLocale = Locale.getDefault()

    beforeSpec { Locale.setDefault(Locale.US) }
    afterSpec { Locale.setDefault(defaultLocale) }

    describe("formatDecimal") {
        it("should keep a single decimal place") {
            7.86f.formatDecimal() shouldBe "7.9"
        }

        it("should pad whole numbers with one decimal place") {
            8f.formatDecimal() shouldBe "8.0"
        }
    }
})
