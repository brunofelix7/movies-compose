package dev.brunofelix.movies.core.presentation.ui.components.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import dev.brunofelix.movies.core.presentation.ui.navigation.MovieRoute

sealed class BottomNavItem(
    val title: String,
    val route: MovieRoute,
    val icon: ImageVector
) {
    data object Popular: BottomNavItem(
        title = "Populars",
        route = MovieRoute.PopularScreen,
        icon = Icons.Default.Movie,
    )

    data object Upcoming: BottomNavItem(
        title = "Upcoming",
        route = MovieRoute.UpcomingScreen,
        icon = Icons.Default.DateRange
    )

    data object Favorite: BottomNavItem(
        title = "Favorites",
        route = MovieRoute.FavoritesScreen,
        icon = Icons.Default.Favorite
    )
}