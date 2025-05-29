package dev.brunofelix.movies.core.presentation.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.MovieRoute
import dev.brunofelix.movies.feature.movie.presentation.view.MovieUpcomingScreen
import dev.brunofelix.movies.feature.movie.presentation.viewmodel.MovieDetailsViewModel
import dev.brunofelix.movies.feature.movie.presentation.viewmodel.MovieUpcomingViewModel

fun NavGraphBuilder.upcomingScreen(
    navController: NavHostController,
    movieUpcomingViewModel: MovieUpcomingViewModel,
    movieDetailsViewModel: MovieDetailsViewModel
) {
    composable<MovieRoute.UpcomingScreen>{
        MovieUpcomingScreen(
            uiState = movieUpcomingViewModel.uiState,
            onItemClick = { movieId ->
                movieDetailsViewModel.getDetails(movieId)
                navController.navigate(MovieRoute.DetailsScreen(movieId))
            }
        )
    }
}