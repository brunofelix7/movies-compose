package dev.brunofelix.movies.core.presentation.util.extension

import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey

val NavKey?.shouldShowBottomBar: Boolean
    get() = this?.let { key ->
        key is MainNavKey.Movies ||
        key is MainNavKey.TvShows ||
        key is MainNavKey.Search ||
        key is MainNavKey.Favorites
    } ?: false
