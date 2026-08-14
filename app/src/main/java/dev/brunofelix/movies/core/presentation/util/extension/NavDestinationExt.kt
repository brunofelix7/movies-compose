package dev.brunofelix.movies.core.presentation.util.extension

import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.AppDestination

val NavKey?.shouldShowBottomBar: Boolean
    get() = this?.let { key ->
        key is AppDestination.Movies ||
        key is AppDestination.TvShows ||
        key is AppDestination.Search ||
        key is AppDestination.Favorites
    } ?: false
