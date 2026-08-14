package dev.brunofelix.movies.feature.popular.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.feature.popular.presentation.ui.MoviePopularScreen
import dev.brunofelix.movies.feature.popular.presentation.viewmodel.MoviePopularViewModel

fun NavGraphBuilder.movieGraph(
    navController: NavController,
    paddingValues: PaddingValues
) {
    composable<AppDestination.Movies> {
        val popularViewModel: MoviePopularViewModel = hiltViewModel()
        MoviePopularScreen(
            paddingValues = paddingValues,
            uiState = popularViewModel.uiState,
            onItemClick = { movieId ->
                navController.navigate(AppDestination.Details(movieId))
            }
        )
    }
}
