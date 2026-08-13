package dev.brunofelix.movies.core.data.util.extension

import retrofit2.HttpException
import retrofit2.Response

inline fun <T, R> Response<T>.mapOrThrow(transform: (T) -> R): R {
    if (isSuccessful) {
        return body()?.let(transform) ?: throw NullPointerException("Response body is null")
    } else {
        throw HttpException(this)
    }
}