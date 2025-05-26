package dev.brunofelix.movies.core.util.exception

import androidx.annotation.StringRes

class LocalException(
    @StringRes val messageRes: Int,
    cause: Throwable? = null
) : Exception(null, cause)