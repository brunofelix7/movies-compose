package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.brunofelix.movies.feature.favorite.presentation.navigation.favoriteGraph
import dev.brunofelix.movies.feature.movie.detail.presentation.navigation.detailGraph
import dev.brunofelix.movies.feature.movie.home.presentation.navigation.movieHomeGraph
import dev.brunofelix.movies.feature.search.presentation.navigation.searchGraph
import dev.brunofelix.movies.feature.tv_show.home.presentation.navigation.tvShowHomeGraph

@Composable
fun AppNavHost(
    paddingValues: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppDestination.Movies
    ) {
        // Movies Screen
        movieHomeGraph(
            navController = navController,
            paddingValues = paddingValues
        )

        // TV Shows Screen
        tvShowHomeGraph(
            navController = navController,
            paddingValues = paddingValues
        )

        // Search Screen
        searchGraph(
            navController = navController,
            paddingValues = paddingValues
        )

        // Details Screen
        detailGraph(
            navController = navController
        )

        // Favorites Screen
        favoriteGraph(
            navController = navController,
            paddingValues = paddingValues
        )
    }
}
