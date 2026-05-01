package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.brunofelix.movies.feature.detail.presentation.ui.MovieDetailsScreen
import dev.brunofelix.movies.feature.detail.presentation.viewmodel.MovieDetailsViewModel
import dev.brunofelix.movies.feature.favorite.presentation.ui.FavoriteScreen
import dev.brunofelix.movies.feature.favorite.presentation.viewmodel.FavoriteViewModel
import dev.brunofelix.movies.feature.popular.presentation.ui.MoviePopularScreen
import dev.brunofelix.movies.feature.popular.presentation.viewmodel.MoviePopularViewModel
import dev.brunofelix.movies.feature.upcoming.presentation.ui.MovieUpcomingScreen
import dev.brunofelix.movies.feature.upcoming.presentation.viewmodel.MovieUpcomingViewModel

@Composable
fun MovieNavHost(
    navController: NavHostController
) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    val moviePopularViewModel: MoviePopularViewModel = hiltViewModel()
    val movieUpcomingViewModel: MovieUpcomingViewModel = hiltViewModel()
    val movieFavoriteViewModel: FavoriteViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = MovieRoute.PopularScreen
    ) {
        composable<MovieRoute.PopularScreen> {
            MoviePopularScreen(
                uiState = moviePopularViewModel.uiState,
                onItemClick = { movieId ->
                    movieDetailsViewModel.getDetails(movieId)
                    navController.navigate(MovieRoute.DetailsScreen(movieId))
                }
            )
        }
        composable<MovieRoute.UpcomingScreen> {
            MovieUpcomingScreen(
                uiState = movieUpcomingViewModel.uiState,
                onItemClick = { movieId ->
                    movieDetailsViewModel.getDetails(movieId)
                    navController.navigate(MovieRoute.DetailsScreen(movieId))
                }
            )
        }
        composable<MovieRoute.DetailsScreen>(
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition
        ) {
            MovieDetailsScreen(
                uiState = movieDetailsViewModel.uiState,
                isFavorite = movieDetailsViewModel.isFavorite,
                onBackClick = {
                    navController.popBackStack()
                },
                onFavoriteClick = {
                    movieDetailsViewModel.onFavoriteToggle()
                }
            )
        }
        composable<MovieRoute.FavoritesScreen> {
            FavoriteScreen(
                uiState = movieFavoriteViewModel.uiState,
                onClick = { movieId ->
                    movieDetailsViewModel.getDetails(movieId)
                    navController.navigate(MovieRoute.DetailsScreen(movieId))
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