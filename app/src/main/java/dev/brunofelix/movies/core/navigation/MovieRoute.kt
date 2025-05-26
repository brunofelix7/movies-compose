package dev.brunofelix.movies.core.navigation

import kotlinx.serialization.Serializable

sealed class MovieRoute {

    @Serializable
    data object PopularScreen : MovieRoute()

    @Serializable
    data object UpcomingScreen : MovieRoute()

    @Serializable
    data object FavoritesScreen : MovieRoute()

    @Serializable
    data class DetailsScreen(val movieId: Long) : MovieRoute()
}