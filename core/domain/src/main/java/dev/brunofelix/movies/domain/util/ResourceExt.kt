package dev.brunofelix.movies.domain.util

/**
 * Transforms the value of a [Resource.Success], leaving a [Resource.Error] untouched.
 */
inline fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> this
    }
}
