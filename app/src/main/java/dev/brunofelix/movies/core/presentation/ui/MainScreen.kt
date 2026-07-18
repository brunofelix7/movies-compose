package dev.brunofelix.movies.core.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.brunofelix.movies.core.presentation.navigation.MovieDestination
import dev.brunofelix.movies.core.presentation.navigation.MovieNavHost
import dev.brunofelix.movies.core.presentation.ui.components.CustomNavBar

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = navBackStackEntry?.destination?.hasRoute<MovieDestination.Details>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar == false) {
                CustomNavBar(navController)
            }
        },
        content = { innerPadding ->
            MovieNavHost(innerPadding, navController)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MainScreen(
        navController = rememberNavController()
    )
}