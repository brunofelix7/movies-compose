package dev.brunofelix.movies.core.presentation.ui.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.ui.navigation.MovieRoute
import dev.brunofelix.movies.feature.details.presentation.viewmodel.MovieDetailsViewModel
import dev.brunofelix.movies.feature.upcoming.presentation.ui.MovieUpcomingScreen
import dev.brunofelix.movies.feature.upcoming.presentation.viewmodel.MovieUpcomingViewModel

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