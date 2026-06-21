package dev.brunofelix.movies.feature.detail.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.MovieRoute
import dev.brunofelix.movies.core.presentation.navigation.enterTransition
import dev.brunofelix.movies.core.presentation.navigation.exitTransition
import dev.brunofelix.movies.core.presentation.navigation.popEnterTransition
import dev.brunofelix.movies.core.presentation.navigation.popExitTransition
import dev.brunofelix.movies.core.presentation.util.extension.sharedViewModel
import dev.brunofelix.movies.feature.detail.presentation.ui.MovieDetailsScreen
import dev.brunofelix.movies.feature.detail.presentation.viewmodel.DetailViewModel

fun NavGraphBuilder.detailNavGraph(
    navController: NavHostController
) {
    composable<MovieRoute.DetailsScreen>(
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) { backStackEntry ->
        val detailViewModel: DetailViewModel = backStackEntry.sharedViewModel(navController)
        val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()
        val isFavorite by detailViewModel.isFavorite.collectAsStateWithLifecycle()

        MovieDetailsScreen(
            uiState = uiState,
            isFavorite = isFavorite,
            onBackClick = {
                navController.popBackStack()
            },
            onFavoriteClick = {
                detailViewModel.onFavoriteToggle()
            }
        )
    }
}