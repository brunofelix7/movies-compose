package dev.brunofelix.movies.core.domain.util.exception

sealed class RemoteException(
    val errorCode: Int? = null,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    class Unauthorized : RemoteException(errorCode = 401)
    class NotFound : RemoteException(errorCode = 404)
    class ServerError : RemoteException(errorCode = 500)
    class NoInternet : RemoteException()
    class Unknown : RemoteException()
    data class ApiError(
        val code: Int,
        override val message: String
    ) : RemoteException(errorCode = code, message = message)
    class General(val messageRes: Int, cause: Throwable? = null) : RemoteException(cause = cause)
}
