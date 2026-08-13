package dev.brunofelix.movies.core.domain.util.exception

sealed class LocalException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    class DatabaseError(cause: Throwable? = null) : LocalException(cause = cause)
    class PermissionDenied : LocalException()
    class DiskFull : LocalException()
    class Unknown : LocalException()
    class General(val messageRes: Int, cause: Throwable? = null) : LocalException(cause = cause)
}
