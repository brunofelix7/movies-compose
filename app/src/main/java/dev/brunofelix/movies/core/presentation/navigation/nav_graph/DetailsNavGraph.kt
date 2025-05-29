package dev.brunofelix.movies.core.presentation.navigation.nav_graph

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.MovieRoute
import dev.brunofelix.movies.feature.movie.presentation.view.MovieDetailsScreen
import dev.brunofelix.movies.feature.movie.presentation.viewmodel.MovieDetailsViewModel

fun NavGraphBuilder.detailsScreen(
    navController: NavHostController,
    movieDetailsViewModel: MovieDetailsViewModel,
) {
    composable<MovieRoute.DetailsScreen> {
        MovieDetailsScreen(
            uiState = movieDetailsViewModel.uiState.collectAsState().value,
            onBackClick = { navController.popBackStack() },
            onFavoriteClick = { movieDetailsViewModel.onFavoriteToggle() }
        )
    }
}