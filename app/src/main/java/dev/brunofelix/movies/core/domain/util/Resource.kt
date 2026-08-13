package dev.brunofelix.movies.core.domain.util

sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Error(val throwable: Throwable) : Resource<Nothing>
}

fun <T> Result<T>.toResource(): Resource<T> {
    return fold(
        onSuccess = { Resource.Success(it) },
        onFailure = { Resource.Error(it) }
    )
}