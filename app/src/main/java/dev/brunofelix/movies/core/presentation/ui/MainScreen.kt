package dev.brunofelix.movies.core.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.core.presentation.navigation.MainNavDisplay
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavViewModel
import dev.brunofelix.movies.core.presentation.ui.components.CustomNavBar
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.MainTopBar
import dev.brunofelix.movies.core.presentation.util.extension.shouldShowBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainNavViewModel = hiltViewModel()
) {
    val backStack by viewModel.backStack.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()

    MainScreenContent(
        backStack = backStack,
        currentTab = currentTab,
        onNavigate = viewModel::navigateTo,
        onBack = viewModel::popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    backStack: List<MainNavKey>,
    currentTab: MainNavKey,
    onNavigate: (MainNavKey) -> Unit,
    onBack: () -> Unit
) {
    val currentRoute = backStack.lastOrNull()
    val isBottomBarVisible = currentRoute?.shouldShowBottomBar ?: false
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    GradientBackground {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                TopBarAnimated(
                    visible = isBottomBarVisible
                ) {
                    MainTopBar(
                        scrollBehavior = scrollBehavior
                    )
                }
            },
            bottomBar = {
                BottomNavigationBarAnimated(
                    visible = isBottomBarVisible
                ) {
                    CustomNavBar(
                        currentTab = currentTab,
                        onNavigate = onNavigate
                    )
                }
            },
            content = { paddingValues ->
                MainNavDisplay(
                    backStack = backStack,
                    onNavigate = onNavigate,
                    onBack = onBack,
                    paddingValues = paddingValues
                )
            }
        )
    }
}

@Composable
private fun TopBarAnimated(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(600)),
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(600)),
        content = { content() }
    )
}

@Composable
private fun BottomNavigationBarAnimated(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(600)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(600)),
        content = { content() }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MainScreenContent(
        backStack = listOf(MainNavKey.Movies),
        currentTab = MainNavKey.Movies,
        onNavigate = {},
        onBack = {}
    )
}
