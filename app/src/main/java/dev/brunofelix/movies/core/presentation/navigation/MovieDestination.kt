package dev.brunofelix.movies.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed class MovieDestination {

    @Serializable
    data object Populars : MovieDestination()

    @Serializable
    data object Upcoming : MovieDestination()

    @Serializable
    data object Favorites : MovieDestination()

    @Serializable
    data class Details(val movieId: Long) : MovieDestination()
}