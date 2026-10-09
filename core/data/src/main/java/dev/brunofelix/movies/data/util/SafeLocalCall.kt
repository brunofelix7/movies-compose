package dev.brunofelix.movies.data.util

import dev.brunofelix.movies.domain.util.exception.LocalException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Runs a local storage [block] on [Dispatchers.IO], wrapping any failure into a
 * [LocalException.DatabaseError] so callers only deal with domain exceptions.
 *
 * @throws CancellationException if the coroutine is cancelled during execution.
 */
suspend fun <T> safeLocalCall(block: suspend () -> T): Result<T> = withContext(Dispatchers.IO) {
    try {
        Result.success(block())
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Result.failure(e as? LocalException ?: LocalException.DatabaseError(e))
    }
}
