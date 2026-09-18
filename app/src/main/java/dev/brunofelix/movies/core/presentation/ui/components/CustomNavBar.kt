package dev.brunofelix.movies.core.presentation.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocalMovies
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
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

sealed class CustomNavBarItem(
    @StringRes val titleResId: Int,
    val route: MainNavKey,
    val icon: ImageVector
) {
    data object Movies: CustomNavBarItem(
        titleResId = R.string.movies,
        route = MainNavKey.Movies,
        icon = Icons.Default.LocalMovies,
    )

    data object TvShows: CustomNavBarItem(
        titleResId = R.string.tv_shows,
        route = MainNavKey.TvShows,
        icon = Icons.Default.LiveTv
    )

    data object Favorites: CustomNavBarItem(
        titleResId = R.string.favorites,
        route = MainNavKey.Favorites,
        icon = Icons.Default.Favorite
    )

    data object Releases: CustomNavBarItem(
        titleResId = R.string.releases,
        route = MainNavKey.Releases,
        icon = Icons.Default.CalendarMonth
    )
}

private val navBarItems = listOf(
    CustomNavBarItem.Movies,
    CustomNavBarItem.TvShows,
    CustomNavBarItem.Favorites,
    CustomNavBarItem.Releases
)

@Composable
fun CustomNavBar(
    currentTab: MainNavKey,
    onNavigate: (MainNavKey) -> Unit,
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
            val isSelected = currentItem.route == currentTab

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
                        text = stringResource(currentItem.titleResId),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onClick = {
                    if (!isSelected) {
                        onNavigate(currentItem.route)
                    }
                }
            )
        }
    }
}
