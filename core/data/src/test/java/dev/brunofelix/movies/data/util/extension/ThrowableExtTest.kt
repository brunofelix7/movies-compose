package dev.brunofelix.movies.data.util.extension

import dev.brunofelix.movies.domain.util.exception.RemoteException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

class ThrowableExtTest : DescribeSpec({

    fun httpException(code: Int) = HttpException(Response.error<Unit>(code, "".toResponseBody()))

    describe("toRemoteException") {
        it("should map connection failures to NoInternet") {
            ConnectException().toRemoteException().shouldBeInstanceOf<RemoteException.NoInternet>()
            UnknownHostException().toRemoteException().shouldBeInstanceOf<RemoteException.NoInternet>()
        }

        it("should map any other IOException to Unknown") {
            IOException().toRemoteException().shouldBeInstanceOf<RemoteException.Unknown>()
        }

        it("should map HTTP 401 to Unauthorized") {
            httpException(401).toRemoteException().shouldBeInstanceOf<RemoteException.Unauthorized>()
        }

        it("should map HTTP 404 to NotFound") {
            httpException(404).toRemoteException().shouldBeInstanceOf<RemoteException.NotFound>()
        }

        it("should map HTTP 5xx to ServerError") {
            httpException(500).toRemoteException().shouldBeInstanceOf<RemoteException.ServerError>()
            httpException(503).toRemoteException().shouldBeInstanceOf<RemoteException.ServerError>()
        }

        it("should map any other HTTP code to Unknown") {
            httpException(418).toRemoteException().shouldBeInstanceOf<RemoteException.Unknown>()
        }

        it("should return a RemoteException as is") {
            val exception = RemoteException.ApiError(code = 7, message = "Invalid API key")

            exception.toRemoteException() shouldBe exception
        }

        it("should map any other throwable to Unknown") {
            IllegalStateException().toRemoteException().shouldBeInstanceOf<RemoteException.Unknown>()
        }
    }
})
