package dev.brunofelix.movies.presentation.util

import android.content.Context
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk

class UiTextTest : DescribeSpec({

    describe("asString") {
        it("should return the value of a DynamicString") {
            UiText.DynamicString("Hello").asString(mockk()) shouldBe "Hello"
        }

        it("should resolve a StringResource with its arguments") {
            val context = mockk<Context> {
                every { getString(42, "Dune", 2) } returns "Dune 2"
            }

            UiText.StringResource(42, "Dune", 2).asString(context) shouldBe "Dune 2"
        }
    }

    describe("StringResource equality") {
        it("should be equal when the resource and the arguments match") {
            UiText.StringResource(1, "a") shouldBe UiText.StringResource(1, "a")
            UiText.StringResource(1, "a").hashCode() shouldBe UiText.StringResource(1, "a").hashCode()
        }

        it("should differ when the resource or the arguments differ") {
            UiText.StringResource(1, "a") shouldNotBe UiText.StringResource(2, "a")
            UiText.StringResource(1, "a") shouldNotBe UiText.StringResource(1, "b")
        }
    }
})
