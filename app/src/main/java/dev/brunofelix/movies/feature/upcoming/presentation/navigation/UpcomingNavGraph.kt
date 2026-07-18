package dev.brunofelix.movies.feature.upcoming.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.MovieDestination
import dev.brunofelix.movies.core.presentation.util.extension.sharedViewModel
import dev.brunofelix.movies.feature.upcoming.presentation.ui.MovieUpcomingScreen
import dev.brunofelix.movies.feature.upcoming.presentation.viewmodel.MovieUpcomingViewModel

fun NavGraphBuilder.upcomingNavGraph(
    navController: NavController
) {
    composable<MovieDestination.Upcoming> { backStackEntry ->
        val upcomingViewModel: MovieUpcomingViewModel = backStackEntry.sharedViewModel(navController)

        MovieUpcomingScreen(
            uiState = upcomingViewModel.uiState,
            onItemClick = { movieId ->
                navController.navigate(MovieDestination.Details(movieId))
            }
        )
    }
}
