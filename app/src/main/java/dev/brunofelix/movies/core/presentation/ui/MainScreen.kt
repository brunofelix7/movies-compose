package dev.brunofelix.movies.core.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.brunofelix.movies.core.presentation.navigation.MovieNavHost
import dev.brunofelix.movies.core.presentation.ui.components.CustomNavBar
import dev.brunofelix.movies.core.presentation.util.extension.shouldShowBottomBar

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isBottomBarVisible = currentDestination.shouldShowBottomBar

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBarAnimated(
                visible = isBottomBarVisible
            ) {
                CustomNavBar(navController)
            }
        },
        content = { innerPadding ->
            val systemNavigationPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            val animatedBottomPadding by animateDpAsState(
                targetValue = if (isBottomBarVisible) {
                    innerPadding.calculateBottomPadding()
                } else {
                    systemNavigationPadding
                },
                animationSpec = tween(durationMillis = 300),
                label = "BottomBarPadding"
            )
            MovieNavHost(
                modifier = Modifier.padding(
                    top = 0.dp,
                    bottom = animatedBottomPadding
                ),
                innerPadding = innerPadding,
                navController = navController)
        }
    )
}

@Composable
private fun BottomNavigationBarAnimated(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300)),
        content = { content() }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MainScreen(
        navController = rememberNavController()
    )
}