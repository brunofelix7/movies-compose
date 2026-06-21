package dev.brunofelix.movies.feature.popular.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.MovieRoute
import dev.brunofelix.movies.core.presentation.util.extension.sharedViewModel
import dev.brunofelix.movies.feature.popular.presentation.ui.MoviePopularScreen
import dev.brunofelix.movies.feature.popular.presentation.viewmodel.MoviePopularViewModel

fun NavGraphBuilder.popularNavGraph(
    navController: NavController
) {
    composable<MovieRoute.PopularScreen> { backStackEntry ->
        val popularViewModel: MoviePopularViewModel = backStackEntry.sharedViewModel(navController)

        MoviePopularScreen(
            uiState = popularViewModel.uiState,
            onItemClick = { movieId ->
                navController.navigate(MovieRoute.DetailsScreen(movieId))
            }
        )
    }
}
