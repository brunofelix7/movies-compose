package dev.brunofelix.movies.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import dev.brunofelix.movies.feature.movie.presentation.view.MovieDetailsScreen
import dev.brunofelix.movies.feature.movie.presentation.view.MoviePopularScreen
import dev.brunofelix.movies.feature.movie.presentation.view.MovieUpcomingScreen
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
        /**
         * Popular movies screen
         */
        composable<MovieRoute.PopularScreen>{
            MoviePopularScreen(
                uiState = moviePopularViewModel.uiState,
                onItemClick = { movieId ->
                    movieDetailsViewModel.getDetails(movieId)
                    navController.navigate(MovieRoute.DetailsScreen(movieId))
                }
            )
        }

        /**
         * Upcoming movies screen
         */
        composable<MovieRoute.UpcomingScreen>{
            MovieUpcomingScreen(
                uiState = movieUpcomingViewModel.uiState,
                onItemClick = { movieId ->
                    movieDetailsViewModel.getDetails(movieId)
                    navController.navigate(MovieRoute.DetailsScreen(movieId))
                }
            )
        }

        /**
         * Favorite movies screen
         */
        composable<MovieRoute.FavoritesScreen>{
            // TODO: Add favorites screen
        }

        /**
         * Details screen
         */
        composable<MovieRoute.DetailsScreen>(
            enterTransition = TransitionAnimation.enterTransition,
            exitTransition = TransitionAnimation.exitTransition,
            popEnterTransition = TransitionAnimation.popEnterTransition,
            popExitTransition = TransitionAnimation.popExitTransition
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<MovieRoute.DetailsScreen>()
            MovieDetailsScreen(
                movieId = route.movieId,
                uiState = movieDetailsViewModel.uiState.value,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}