package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.brunofelix.movies.core.presentation.navigation.nav_graph.detailsScreen
import dev.brunofelix.movies.core.presentation.navigation.nav_graph.favoriteScreen
import dev.brunofelix.movies.core.presentation.navigation.nav_graph.popularScreen
import dev.brunofelix.movies.core.presentation.navigation.nav_graph.upcomingScreen
import dev.brunofelix.movies.feature.movie.presentation.viewmodel.MovieDetailsViewModel
import dev.brunofelix.movies.feature.movie.presentation.viewmodel.MoviePopularViewModel
import dev.brunofelix.movies.feature.movie.presentation.viewmodel.MovieUpcomingViewModel

@Composable
fun MovieNavHost(
    navController: NavHostController
) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    val moviePopularViewModel: MoviePopularViewModel = hiltViewModel()
    val movieUpcomingViewModel: MovieUpcomingViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = MovieRoute.PopularScreen
    ) {
        popularScreen(
            navController = navController,
            moviePopularViewModel = moviePopularViewModel,
            movieDetailsViewModel = movieDetailsViewModel
        )
        upcomingScreen(
            navController = navController,
            movieUpcomingViewModel = movieUpcomingViewModel,
            movieDetailsViewModel = movieDetailsViewModel
        )
        detailsScreen(
            navController = navController,
            movieDetailsViewModel = movieDetailsViewModel
        )
        favoriteScreen(
            navController = navController
        )
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}