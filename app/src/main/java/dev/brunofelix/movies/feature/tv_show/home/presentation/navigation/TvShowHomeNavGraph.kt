package dev.brunofelix.movies.feature.tv_show.home.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.navigation.AppDestination
import dev.brunofelix.movies.feature.tv_show.home.presentation.ui.TvShowHomeScreen

fun NavGraphBuilder.tvShowHomeGraph(
    navController: NavController,
    paddingValues: PaddingValues
) {
    composable<AppDestination.TvShows> {
        TvShowHomeScreen(
            onItemClick = { tvShowId ->
                navController.navigate(AppDestination.Details(tvShowId))
            },
            paddingValues = paddingValues
        )
    }
}
