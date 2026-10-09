package dev.brunofelix.movies.data.util.extension

import dev.brunofelix.movies.domain.util.exception.RemoteException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ResponseExtTest : DescribeSpec({

    describe("mapOrThrow with transform") {
        it("should transform the body of a successful response") {
            Response.success(21).mapOrThrow { it * 2 } shouldBe 42
        }

        it("should throw when a successful response has no body") {
            shouldThrow<NullPointerException> {
                Response.success<Int>(null).mapOrThrow { it }
            }
        }

        it("should throw the mapped RemoteException for an error response") {
            shouldThrow<RemoteException.NotFound> {
                Response.error<Int>(404, "".toResponseBody()).mapOrThrow { it }
            }
        }
    }

    describe("mapOrThrow without body") {
        it("should return normally for a successful response") {
            Response.success(Unit).mapOrThrow()
        }

        it("should throw the mapped RemoteException for an error response") {
            shouldThrow<RemoteException.Unauthorized> {
                Response.error<Unit>(401, "".toResponseBody()).mapOrThrow()
            }
        }
    }

    describe("toResult") {
        it("should wrap the body of a successful response") {
            Response.success("body").toResult() shouldBe Result.success("body")
        }

        it("should fail when a successful response has no body") {
            Response.success<String>(null).toResult().exceptionOrNull()
                .shouldBeInstanceOf<NullPointerException>()
        }

        it("should fail with the mapped RemoteException for an error response") {
            Response.error<String>(500, "".toResponseBody()).toResult().exceptionOrNull()
                .shouldBeInstanceOf<RemoteException.ServerError>()
        }
    }
})
