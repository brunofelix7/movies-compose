package dev.brunofelix.movies.core.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface MainNavKey : NavKey {

    @Serializable
    data object Movies : MainNavKey

    @Serializable
    data object TvShows : MainNavKey

    @Serializable
    data object Search : MainNavKey

    @Serializable
    data object Favorites : MainNavKey

    @Serializable
    data class MovieDetails(val id: Long) : MainNavKey

    @Serializable
    data class TvShowDetails(val id: Long) : MainNavKey
}