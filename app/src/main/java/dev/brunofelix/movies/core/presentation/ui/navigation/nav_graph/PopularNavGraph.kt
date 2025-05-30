package dev.brunofelix.movies.core.presentation.ui.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.ui.navigation.MovieRoute
import dev.brunofelix.movies.feature.details.presentation.viewmodel.MovieDetailsViewModel
import dev.brunofelix.movies.feature.popular.presentation.ui.MoviePopularScreen
import dev.brunofelix.movies.feature.popular.presentation.viewmodel.MoviePopularViewModel

fun NavGraphBuilder.popularScreen(
    navController: NavHostController,
    moviePopularViewModel: MoviePopularViewModel,
    movieDetailsViewModel: MovieDetailsViewModel
) {
    composable<MovieRoute.PopularScreen>{
        MoviePopularScreen(
            uiState = moviePopularViewModel.uiState,
            onItemClick = { movieId ->
                movieDetailsViewModel.getDetails(movieId)
                navController.navigate(MovieRoute.DetailsScreen(movieId))
            }
        )
    }
}