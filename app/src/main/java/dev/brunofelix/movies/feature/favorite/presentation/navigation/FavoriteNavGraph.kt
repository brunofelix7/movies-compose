package dev.brunofelix.movies.feature.favorite.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.MovieRoute
import dev.brunofelix.movies.core.presentation.util.extension.sharedViewModel
import dev.brunofelix.movies.feature.favorite.presentation.ui.FavoriteScreen
import dev.brunofelix.movies.feature.favorite.presentation.viewmodel.FavoriteViewModel

fun NavGraphBuilder.favoriteNavGraph(
    navController: NavController
) {
    composable<MovieRoute.FavoritesScreen> { backStackEntry ->
        val favoriteViewModel: FavoriteViewModel = backStackEntry.sharedViewModel(navController)

        FavoriteScreen(
            uiState = favoriteViewModel.uiState,
            onClick = { movieId ->
                navController.navigate(MovieRoute.DetailsScreen(movieId))
            }
        )
    }
}
