package dev.brunofelix.movies.data.util

import app.cash.turbine.test
import dev.brunofelix.movies.domain.util.exception.RemoteException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.net.UnknownHostException

private interface FakeApi {
    suspend fun number(): Response<Int>
    suspend fun empty(): Response<Unit>
    fun stream(): Flow<Int>
}

private class FakeRemoteDataSource(api: FakeApi) : BaseRemoteDataSource<FakeApi>(api) {
    suspend fun doubled(): Result<Int> = safeApiCall(call = { number() }, transform = { it * 2 })
    suspend fun empty(): Result<Int> = safeApiCall(call = { empty() })
    fun stream(): Flow<String> = safeFlowApiCall(call = { stream() }, transform = { "#$it" })
}

class BaseRemoteDataSourceTest : DescribeSpec({

    fun dataSource(
        number: suspend () -> Response<Int> = { Response.success(1) },
        empty: suspend () -> Response<Unit> = { Response.success(Unit) },
        stream: () -> Flow<Int> = { flowOf() }
    ) = FakeRemoteDataSource(object : FakeApi {
        override suspend fun number() = number()
        override suspend fun empty() = empty()
        override fun stream() = stream()
    })

    describe("safeApiCall with transform") {
        it("should return the transformed body on success") {
            runTest {
                dataSource(number = { Response.success(21) }).doubled() shouldBe Result.success(42)
            }
        }

        it("should map an error response to a RemoteException") {
            runTest {
                val result = dataSource(number = { Response.error(404, "".toResponseBody()) }).doubled()

                result.exceptionOrNull().shouldBeInstanceOf<RemoteException.NotFound>()
            }
        }

        it("should map a thrown exception to a RemoteException") {
            runTest {
                val result = dataSource(number = { throw UnknownHostException() }).doubled()

                result.exceptionOrNull().shouldBeInstanceOf<RemoteException.NoInternet>()
            }
        }
    }

    describe("safeApiCall without body") {
        it("should return the status code on success") {
            runTest {
                dataSource(empty = { Response.success(204, Unit) }).empty() shouldBe Result.success(204)
            }
        }

        it("should map an error response to a RemoteException") {
            runTest {
                val result = dataSource(empty = { Response.error(500, "".toResponseBody()) }).empty()

                result.exceptionOrNull().shouldBeInstanceOf<RemoteException.ServerError>()
            }
        }
    }

    describe("safeFlowApiCall") {
        it("should emit the transformed values") {
            runTest {
                dataSource(stream = { flowOf(1, 2) }).stream().test {
                    awaitItem() shouldBe "#1"
                    awaitItem() shouldBe "#2"
                    awaitComplete()
                }
            }
        }

        it("should rethrow upstream failures as RemoteException") {
            runTest {
                dataSource(stream = { flow { throw UnknownHostException() } }).stream().test {
                    awaitError().shouldBeInstanceOf<RemoteException.NoInternet>()
                }
            }
        }
    }
})
