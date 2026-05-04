package dev.brunofelix.movies.core.util

import timber.log.Timber

const val APP_LOG = "APP_LOG"

fun logInfo(message: String) {
    Timber.tag(APP_LOG).i("Info -> $message")
}

fun logError(exception: Exception) {
    Timber.tag(APP_LOG).e(exception, "Error -> ${exception.message}")
}