package dev.brunofelix.movies.data.util.extension

import dev.brunofelix.movies.core.data.BuildConfig

/**
 * Turns the relative image paths TMDB returns into absolute URLs.
 */
fun String.toPosterUrl(): String = "${BuildConfig.BASE_URL_IMAGE}$this"

fun String.toBackdropUrl(): String = "${BuildConfig.BASE_URL_IMAGE}$this"

fun String.toStillUrl(): String = "${BuildConfig.BASE_URL_IMAGE}$this"

fun String.toProfileUrl(): String = "${BuildConfig.BASE_URL_IMAGE}$this"
