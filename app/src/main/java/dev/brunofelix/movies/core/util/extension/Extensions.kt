package dev.brunofelix.movies.core.util.extension

import dev.brunofelix.movies.BuildConfig

fun String?.toPostUrl() = "${BuildConfig.BASE_URL_IMAGE}$this"

fun String?.toBackdropUrl() = "${BuildConfig.BASE_URL_IMAGE}$this"

fun Float.formatDecimal() = "%.1f".format(this)