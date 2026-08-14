package dev.brunofelix.movies.feature.movie.detail.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.core.presentation.navigation.enterTransition
import dev.brunofelix.movies.core.presentation.navigation.exitTransition
import dev.brunofelix.movies.core.presentation.navigation.popEnterTransition
import dev.brunofelix.movies.core.presentation.navigation.popExitTransition
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.MovieDetailRoute

fun NavGraphBuilder.detailGraph(
    navController: NavController
) {
    composable<AppDestination.Details>(
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) {
        MovieDetailRoute(
            movieId = it.toRoute<AppDestination.Details>().id,
            navController = navController
        )
    }
}