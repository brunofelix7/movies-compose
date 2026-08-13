package dev.brunofelix.movies.core.data.util

import timber.log.Timber

/**
 * Log tag used for all application logs.
 */
const val APP_LOG = "APP_LOG"

/**
 * Logs an information message using [Timber].
 * @param message The message to log.
 */
fun logInfo(message: String) {
    Timber.tag(APP_LOG).i("Info -> $message")
}

/**
 * Logs an error exception using [Timber].
 * @param exception The exception to log.
 */
fun logError(exception: Exception) {
    Timber.tag(APP_LOG).e(exception, "Error -> ${exception.message}")
}