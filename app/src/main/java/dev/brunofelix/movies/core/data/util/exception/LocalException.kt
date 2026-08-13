package dev.brunofelix.movies.core.data.util.exception

import androidx.annotation.StringRes

class LocalException(
    @get:StringRes val messageRes: Int = 0,
    cause: Throwable? = null
) : Exception(cause)