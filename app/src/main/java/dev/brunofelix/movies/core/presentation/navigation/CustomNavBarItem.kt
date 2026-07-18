package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector

sealed class CustomNavBarItem(
    val title: String,
    val route: MovieDestination,
    val icon: ImageVector
) {
    data object Popular: CustomNavBarItem(
        title = "Populars",
        route = MovieDestination.Populars,
        icon = Icons.Default.Movie,
    )

    data object Upcoming: CustomNavBarItem(
        title = "Upcoming",
        route = MovieDestination.Upcoming,
        icon = Icons.Default.DateRange
    )

    data object Favorite: CustomNavBarItem(
        title = "Favorites",
        route = MovieDestination.Favorites,
        icon = Icons.Default.Favorite
    )
}