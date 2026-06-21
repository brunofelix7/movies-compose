package dev.brunofelix.movies.feature.favorite.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.MovieRoute
import dev.brunofelix.movies.core.presentation.util.extension.sharedViewModel
import dev.brunofelix.movies.feature.favorite.presentation.state.FavoriteUiState
import dev.brunofelix.movies.feature.favorite.presentation.ui.FavoriteScreen
import dev.brunofelix.movies.feature.favorite.presentation.viewmodel.FavoriteViewModel

fun NavGraphBuilder.favoriteNavGraph(
    navController: NavController
) {
    composable<MovieRoute.FavoritesScreen> {
        val favoriteViewModel: FavoriteViewModel = it.sharedViewModel(navController)
        val uiState by favoriteViewModel.uiState.collectAsStateWithLifecycle()

        FavoriteScreen(
            uiState = FavoriteUiState(
                state = uiState,
                onCardClick = { movieId ->
                    navController.navigate(MovieRoute.DetailsScreen(movieId))
                }
            )
        )
    }
}
