package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

sealed class CustomNavBarItem(
    val title: String,
    val route: AppDestination,
    val icon: ImageVector
) {
    data object Popular: CustomNavBarItem(
        title = "Movies",
        route = AppDestination.Movies,
        icon = Icons.Default.LocalMovies,
    )

    data object Upcoming: CustomNavBarItem(
        title = "TV Shows",
        route = AppDestination.TvShows,
        icon = Icons.Default.LiveTv
    )
    data object Search: CustomNavBarItem(
        title = "Search",
        route = AppDestination.Search,
        icon = Icons.Default.Search
    )

    data object Favorite: CustomNavBarItem(
        title = "Favorites",
        route = AppDestination.Favorites,
        icon = Icons.Default.Favorite
    )
}

private val navBarItems = listOf(
    CustomNavBarItem.Popular,
    CustomNavBarItem.Upcoming,
    CustomNavBarItem.Search,
    CustomNavBarItem.Favorite
)

@Composable
fun CustomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Colors.redPrimary,
        unselectedIconColor = Colors.white,
        selectedTextColor = Colors.redPrimary,
        unselectedTextColor = Colors.white,
        indicatorColor = Colors.redPrimary.copy(alpha = 0.2F)
    )

    NavigationBar(
        containerColor = Colors.blackPrimary.copy(alpha = 0.85F),
        tonalElevation = 0.dp,
        modifier = modifier
    ) {
        navBarItems.forEach { currentItem ->
            val isSelected = currentDestination?.hasRoute(currentItem.route::class) ?: false

            NavigationBarItem(
                selected = isSelected,
                colors = itemColors,
                icon = {
                    Icon(
                        imageVector = currentItem.icon,
                        contentDescription = stringResource(R.string.navbar_icon)
                    )
                },
                label = {
                    Text(
                        text = currentItem.title,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onClick = {
                    if (!isSelected) {
                        navController.navigate(currentItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CustomNavBar(
        navController = rememberNavController()
    )
}