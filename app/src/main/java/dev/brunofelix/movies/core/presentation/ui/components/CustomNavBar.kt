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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.navigation.NavigationState
import dev.brunofelix.movies.core.presentation.navigation.Navigator
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

sealed class CustomNavBarItem(
    val title: String,
    val route: MainNavKey,
    val icon: ImageVector
) {
    data object Popular: CustomNavBarItem(
        title = "Movies",
        route = MainNavKey.Movies,
        icon = Icons.Default.LocalMovies,
    )

    data object Upcoming: CustomNavBarItem(
        title = "TV Shows",
        route = MainNavKey.TvShows,
        icon = Icons.Default.LiveTv
    )
    data object Search: CustomNavBarItem(
        title = "Search",
        route = MainNavKey.Search,
        icon = Icons.Default.Search
    )

    data object Favorite: CustomNavBarItem(
        title = "Favorites",
        route = MainNavKey.Favorites,
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
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
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
            val isSelected = currentItem.route == navigationState.topLevelRoute

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
                        navigator.navigate(currentItem.route)
                    }
                }
            )
        }
    }
}
