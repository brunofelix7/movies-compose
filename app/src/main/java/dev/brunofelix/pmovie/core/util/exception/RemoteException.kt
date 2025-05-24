package dev.brunofelix.pmovie.core.util.exception

import androidx.annotation.StringRes

class RemoteException(
    @StringRes val messageRes: Int,
    cause: Throwable? = null
) : Exception(null, cause)