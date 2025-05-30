package dev.brunofelix.movies.core.presentation.ui.components.navbar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.navigation.MovieRoute
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Popular,
        BottomNavItem.Upcoming,
        BottomNavItem.Favorite
    )
    val selectedItem = remember {
        mutableStateOf<MovieRoute>(MovieRoute.PopularScreen)
    }

    NavigationBar(
        containerColor = Colors.blackPrimary,
        modifier = modifier
    ) {
        items.forEach { currentItem ->
            val selectedItemColor = if (selectedItem.value == currentItem.route) {
                Colors.redPrimary
            } else {
                Colors.lightGray
            }

            NavigationBarItem(
                selected = selectedItem.value == currentItem.route,
                icon = {
                    Icon(
                        imageVector = currentItem.icon,
                        tint = selectedItemColor,
                        contentDescription = stringResource(R.string.navbar_icon)
                    )
                },
                label = {
                    Text(
                        text = currentItem.title,
                        color = selectedItemColor
                    )
                },
                onClick = {
                    selectedItem.value = currentItem.route
                    navController.navigate(currentItem.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBarPreview() {
    BottomNavBar(
        navController = rememberNavController()
    )
}