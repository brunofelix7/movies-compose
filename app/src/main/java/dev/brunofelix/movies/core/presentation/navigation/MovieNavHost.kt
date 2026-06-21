package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.brunofelix.movies.feature.detail.presentation.navigation.detailNavGraph
import dev.brunofelix.movies.feature.favorite.presentation.navigation.favoriteNavGraph
import dev.brunofelix.movies.feature.popular.presentation.navigation.popularNavGraph
import dev.brunofelix.movies.feature.upcoming.presentation.navigation.upcomingNavGraph

@Composable
fun MovieNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MovieRoute.PopularScreen
    ) {
        popularNavGraph(navController)
        upcomingNavGraph(navController)
        detailNavGraph(navController)
        favoriteNavGraph(navController)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
