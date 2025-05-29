package dev.brunofelix.movies.core.presentation.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.MovieRoute
import dev.brunofelix.movies.feature.movie.presentation.view.MoviePopularScreen
import dev.brunofelix.movies.feature.movie.presentation.viewmodel.MovieDetailsViewModel
import dev.brunofelix.movies.feature.movie.presentation.viewmodel.MoviePopularViewModel

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