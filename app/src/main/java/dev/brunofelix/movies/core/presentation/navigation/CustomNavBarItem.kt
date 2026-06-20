package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector

sealed class CustomNavBarItem(
    val title: String,
    val route: MovieRoute,
    val icon: ImageVector
) {
    data object Popular: CustomNavBarItem(
        title = "Populars",
        route = MovieRoute.PopularScreen,
        icon = Icons.Default.Movie,
    )

    data object Upcoming: CustomNavBarItem(
        title = "Upcoming",
        route = MovieRoute.UpcomingScreen,
        icon = Icons.Default.DateRange
    )

    data object Favorite: CustomNavBarItem(
        title = "Favorites",
        route = MovieRoute.FavoritesScreen,
        icon = Icons.Default.Favorite
    )
}