package dev.brunofelix.pmovie.core.util.exception

import androidx.annotation.StringRes

class LocalException(
    @StringRes val messageRes: Int,
    cause: Throwable? = null
) : Exception(null, cause)