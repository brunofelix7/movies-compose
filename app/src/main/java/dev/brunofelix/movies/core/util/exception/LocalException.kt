package dev.brunofelix.movies.core.util.exception

import androidx.annotation.StringRes

class LocalException(
    @get:StringRes val messageRes: Int = 0,
    cause: Throwable? = null
) : Exception(cause)