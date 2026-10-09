package dev.brunofelix.movies

import android.app.Activity
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.ui.MainScreen
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SYSTEM_SPLASH_FADE_MILLIS = 250L

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getLanguage: GetLanguageUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setOnExitAnimationListener(::fadeOutSystemSplash)
        applySavedAppLocale()
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()),
            statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb())
        )
        setContent {
            val view = LocalView.current
            SideEffect {
                val window = (view.context as Activity).window
                val controller = WindowInsetsControllerCompat(window, view)
                // Light icons on both bars, since every screen is dark.
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = false
            }
            PMovieTheme {
                MainScreen()
            }
        }
    }

    /**
     * Mirrors the language picked in Settings onto the app locale, so the app's own strings and
     * the dates follow the same choice that already drives the API.
     *
     * The comparison matters: AppCompat restores the locale on its own before this runs, and
     * calling [AppCompatDelegate.setApplicationLocales] with the locale already in place would
     * recreate the activity on every start.
     */
    private fun applySavedAppLocale() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getLanguage()
                    .map { it.code }
                    .distinctUntilChanged()
                    .collect { tag ->
                        val current = AppCompatDelegate.getApplicationLocales()
                        if (current.get(0)?.toLanguageTag() != tag) {
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(tag)
                            )
                        }
                    }
            }
        }
    }

    /**
     * Fades the system splash out instead of letting it pop away, handing over to the splash
     * destination of the navigation graph.
     */
    private fun fadeOutSystemSplash(provider: SplashScreenViewProvider) {
        provider.view.animate()
            .alpha(0f)
            .setDuration(SYSTEM_SPLASH_FADE_MILLIS)
            .withEndAction(provider::remove)
            .start()
    }
}
