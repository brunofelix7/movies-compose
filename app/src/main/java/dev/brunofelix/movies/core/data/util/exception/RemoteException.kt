package dev.brunofelix.movies.core.data.util.exception

import androidx.annotation.StringRes

class RemoteException(
    @StringRes val messageRes: Int,
    cause: Throwable? = null
) : Exception(null, cause)