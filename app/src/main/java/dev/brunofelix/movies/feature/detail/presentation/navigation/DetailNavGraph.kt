package dev.brunofelix.movies.feature.detail.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.brunofelix.movies.core.presentation.navigation.MovieRoute
import dev.brunofelix.movies.core.presentation.navigation.enterTransition
import dev.brunofelix.movies.core.presentation.navigation.exitTransition
import dev.brunofelix.movies.core.presentation.navigation.popEnterTransition
import dev.brunofelix.movies.core.presentation.navigation.popExitTransition
import dev.brunofelix.movies.core.presentation.util.extension.sharedViewModel
import dev.brunofelix.movies.feature.detail.presentation.state.DetailUiState
import dev.brunofelix.movies.feature.detail.presentation.ui.DetailScreen
import dev.brunofelix.movies.feature.detail.presentation.viewmodel.DetailViewModel

fun NavGraphBuilder.detailNavGraph(
    navController: NavController
) {
    composable<MovieRoute.DetailsScreen>(
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) {
        val detailsScreen = it.toRoute<MovieRoute.DetailsScreen>()
        val detailViewModel: DetailViewModel = it.sharedViewModel(navController)
        val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()
        val isFavorite by detailViewModel.isFavorite.collectAsStateWithLifecycle()

        LaunchedEffect(detailsScreen.movieId) {
            detailViewModel.getDetails(detailsScreen.movieId)
        }

        DetailScreen(
            uiState = DetailUiState(
                state = uiState,
                isFavorite = isFavorite,
                onBack = {
                    navController.popBackStack()
                },
                onFavorite = {
                    detailViewModel.onFavoriteToggle()
                }
            )
        )
    }
}