package dev.brunofelix.movies.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Movies : AppDestination

    @Serializable
    data object TvShows : AppDestination

    @Serializable
    data object Search : AppDestination

    @Serializable
    data object Favorites : AppDestination

    @Serializable
    data class Details(val id: Long) : AppDestination
}