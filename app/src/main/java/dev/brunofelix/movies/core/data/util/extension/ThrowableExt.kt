package dev.brunofelix.movies.core.data.util.extension

import dev.brunofelix.movies.core.domain.util.exception.RemoteException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toRemoteException(): RemoteException {
    return when (this) {
        is IOException -> {
            when (this) {
                is ConnectException, is UnknownHostException -> RemoteException.NoInternet()
                else -> RemoteException.Unknown()
            }
        }
        is HttpException -> {
            when (code()) {
                401 -> RemoteException.Unauthorized()
                404 -> RemoteException.NotFound()
                in 500..599 -> RemoteException.ServerError()
                else -> RemoteException.Unknown()
            }
        }
        is RemoteException -> this
        else -> RemoteException.Unknown()
    }
}
