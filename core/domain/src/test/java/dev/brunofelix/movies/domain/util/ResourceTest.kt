package dev.brunofelix.movies.domain.util

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ResourceTest : DescribeSpec({

    val failure = IllegalStateException("boom")

    describe("fold") {
        it("should call onSuccess with the data of a Success") {
            val result = Resource.Success(2).fold(
                onSuccess = { it * 10 },
                onFailure = { -1 }
            )

            result shouldBe 20
        }

        it("should call onFailure with the throwable of an Error") {
            val result = Resource.Error(failure).fold(
                onSuccess = { "success" },
                onFailure = { it.message }
            )

            result shouldBe "boom"
        }
    }

    describe("map") {
        it("should transform the data of a Success") {
            Resource.Success(3).map { it.toString() } shouldBe Resource.Success("3")
        }

        it("should keep an Error untouched") {
            val error: Resource<Int> = Resource.Error(failure)

            error.map { it * 2 } shouldBe Resource.Error(failure)
        }
    }

    describe("toResource") {
        it("should convert a successful Result into a Success") {
            Result.success("value").toResource() shouldBe Resource.Success("value")
        }

        it("should convert a failed Result into an Error with the same throwable") {
            val resource = Result.failure<String>(failure).toResource()

            resource.shouldBeInstanceOf<Resource.Error>()
            resource.throwable shouldBe failure
        }
    }
})
