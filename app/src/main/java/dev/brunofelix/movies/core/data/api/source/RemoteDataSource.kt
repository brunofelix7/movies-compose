package dev.brunofelix.movies.core.data.api.source

import dev.brunofelix.movies.core.util.extension.mapOrThrow
import retrofit2.Response

abstract class RemoteDataSource<T : Any>(
    private val api: T
) {
    protected suspend fun <R, S> safeApiCall(
        call: suspend T.() -> Response<S>,
        transform: (S) -> R
    ): Result<R> {
        return runCatching {
            api.call().mapOrThrow(transform)
        }
    }

    protected suspend fun safeApiCall(
        call: suspend T.() -> Response<Unit>
    ): Result<Unit> {
        return runCatching {
            api.call().mapOrThrow()
        }
    }
}