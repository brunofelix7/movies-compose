package dev.brunofelix.movies.data.util

import dev.brunofelix.movies.domain.util.exception.LocalException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest

class SafeLocalCallTest : DescribeSpec({

    describe("safeLocalCall") {
        it("should wrap the block result in a success") {
            runTest {
                safeLocalCall { 7 } shouldBe Result.success(7)
            }
        }

        it("should wrap a failure into a DatabaseError keeping the cause") {
            runTest {
                val cause = IllegalStateException("disk")

                val error = safeLocalCall { throw cause }.exceptionOrNull()

                error.shouldBeInstanceOf<LocalException.DatabaseError>()
                error.cause shouldBe cause
            }
        }

        it("should keep a LocalException as is") {
            runTest {
                val exception = LocalException.DiskFull()

                safeLocalCall { throw exception }.exceptionOrNull() shouldBe exception
            }
        }

        it("should rethrow cancellation") {
            runTest {
                shouldThrow<CancellationException> {
                    safeLocalCall { throw CancellationException("cancelled") }
                }
            }
        }
    }
})
