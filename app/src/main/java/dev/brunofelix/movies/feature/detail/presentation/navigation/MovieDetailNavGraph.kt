package dev.brunofelix.movies.feature.detail.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import dev.brunofelix.movies.feature.detail.presentation.state.MovieDetailState
import dev.brunofelix.movies.feature.detail.presentation.ui.DetailScreen
import dev.brunofelix.movies.feature.detail.presentation.viewmodel.DetailViewModel

fun NavGraphBuilder.movieDetailGraph(
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
        val onBack = remember<() -> Unit> { { navController.popBackStack() } }
        val onFavorite = remember<() -> Unit> { { detailViewModel.onFavoriteToggle() } }
        val onWatchTrailer = remember<() -> Unit> { { /* chamar lógica do trailer */ } }
        val state = remember(uiState, isFavorite) {
            MovieDetailState(
                uiState = uiState,
                isFavorite = isFavorite,
                onBack = onBack,
                onFavorite = onFavorite,
                onWatchTrailer = onWatchTrailer
            )
        }
        LaunchedEffect(detailsScreen.movieId) {
            detailViewModel.getDetails(detailsScreen.movieId)
        }
        DetailScreen(state)
    }
}