package dev.brunofelix.movies.core.domain.util

/**
 * A sealed interface representing a state of a resource that can be either [Success] or [Error].
 *
 * @param T The type of the data held by the resource.
 */
sealed interface Resource<out T> {
    /**
     * Represents a successful state with the associated [data].
     *
     * @param T The type of the data.
     * @property data The data returned on success.
     */
    data class Success<out T>(val data: T) : Resource<T>

    /**
     * Represents an error state with the associated [throwable].
     *
     * @property throwable The exception or error that occurred.
     */
    data class Error(val throwable: Throwable) : Resource<Nothing>
}

/**
 * Extension function to convert a [Result] to a [Resource].
 *
 * @param T The type of the value in the [Result].
 * @return A [Resource.Success] if the [Result] is successful, or [Resource.Error] if it failed.
 */
fun <T> Result<T>.toResource(): Resource<T> {
    return fold(
        onSuccess = { Resource.Success(it) },
        onFailure = { Resource.Error(it) }
    )
}