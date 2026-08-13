package dev.brunofelix.movies.core.data.util.extension

import retrofit2.HttpException
import retrofit2.Response

inline fun <T, R> Response<T>.mapOrThrow(transform: (T) -> R): R {
    if (isSuccessful) {
        return body()?.let(transform) ?: throw NullPointerException("Response body is null")
    } else {
        throw HttpException(this).toRemoteException()
    }
}

fun <T> Response<T>.toResult(): Result<T> {
    return try {
        if (isSuccessful) {
            val body = body()
            if (body != null) {
                Result.success(body)
            } else {
                Result.failure(NullPointerException("Response body is null"))
            }
        } else {
            Result.failure(HttpException(this).toRemoteException())
        }
    } catch (e: Exception) {
        Result.failure(e.toRemoteException())
    }
}