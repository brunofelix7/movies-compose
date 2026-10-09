package dev.brunofelix.movies.presentation.components

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
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.ScrimBar
import dev.brunofelix.movies.designsystem.theme.elevation0
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.navigation.Route

private const val INDICATOR_ALPHA = 0.2f

sealed class CustomNavBarItem(
    @param:StringRes val titleResId: Int,
    val route: Route,
    val icon: ImageVector
) {
    data object Movies : CustomNavBarItem(
        titleResId = R.string.movies,
        route = Route.Movies,
        icon = Icons.Default.LocalMovies
    )

    data object TvShows : CustomNavBarItem(
        titleResId = R.string.tv_shows,
        route = Route.TvShows,
        icon = Icons.Default.LiveTv
    )

    data object Favorites : CustomNavBarItem(
        titleResId = R.string.favorites,
        route = Route.Favorites,
        icon = Icons.Default.Favorite
    )

    data object Releases : CustomNavBarItem(
        titleResId = R.string.releases,
        route = Route.Releases,
        icon = Icons.Default.CalendarMonth
    )
}

private val navBarItems = listOf(
    CustomNavBarItem.Movies,
    CustomNavBarItem.TvShows,
    CustomNavBarItem.Favorites,
    CustomNavBarItem.Releases
)

/**
 * Bottom navigation bar of the main tabs. Each item has a visible label, so the icons are
 * decorative.
 */
@Composable
fun CustomNavBar(
    currentTab: Route,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = INDICATOR_ALPHA)
    )

    NavigationBar(
        containerColor = ScrimBar,
        tonalElevation = elevation0,
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
                        contentDescription = null
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

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        CustomNavBar(currentTab = Route.Movies, onNavigate = {})
    }
}
