package dev.brunofelix.movies.core.presentation.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.WindowInsetsControllerCompat
import dagger.hilt.android.AndroidEntryPoint
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import kotlinx.coroutines.delay

private const val SystemSplashFadeMillis = 250L
private const val SplashHoldMillis = 900L
private const val SplashFadeMillis = 500

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setOnExitAnimationListener(::fadeOutSystemSplash)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(Color.Transparent.hashCode()),
            statusBarStyle = SystemBarStyle.dark(Color.Transparent.hashCode())
        )
        setContent {
            val view = LocalView.current
            SideEffect {
                val window = (view.context as Activity).window
                val controller = WindowInsetsControllerCompat(window, view)
                // Status Bar icons colors (false for light icons, true for dark icons)
                controller.isAppearanceLightStatusBars = false

                // Navigation Bar icons colors (true for dark icons, false for light icons)
                controller.isAppearanceLightNavigationBars = false
            }
            PMovieTheme {
                SplashHost {
                    MainScreen()
                }
            }
        }
    }

    /**
     * Hands the system splash over to [SplashScreen] instead of letting it pop away, so the
     * black background can warm up into the dark red gradient without a visible cut.
     */
    private fun fadeOutSystemSplash(provider: SplashScreenViewProvider) {
        provider.view.animate()
            .alpha(0F)
            .setDuration(SystemSplashFadeMillis)
            .withEndAction(provider::remove)
            .start()
    }
}

@Composable
private fun SplashHost(content: @Composable () -> Unit) {
    var isSplashVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(SplashHoldMillis)
        isSplashVisible = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        AnimatedVisibility(
            visible = isSplashVisible,
            enter = EnterTransition.None,
            exit = fadeOut(animationSpec = tween(SplashFadeMillis))
        ) {
            SplashScreen()
        }
    }
}
