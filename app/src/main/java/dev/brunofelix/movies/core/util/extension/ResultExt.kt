package dev.brunofelix.movies.core.util.extension

import dev.brunofelix.movies.core.data.util.Resource

fun <T> T?.toSuccessOrEmpty(): Resource<T> {
    return this?.let { Resource.Success(it) } ?: Resource.Error(NullPointerException("Null value"))
}

fun <T> Result<T?>.toResource(): Resource<T> {
    return fold(
        onSuccess = { it.toSuccessOrEmpty() },
        onFailure = { Resource.Error(it) }
    )
}