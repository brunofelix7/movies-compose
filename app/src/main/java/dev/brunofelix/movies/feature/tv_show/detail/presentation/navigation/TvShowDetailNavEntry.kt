package dev.brunofelix.movies.feature.tv_show.detail.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey

fun EntryProviderScope<NavKey>.tvShowDetailEntry(
    onBack: () -> Unit
) {
    entry<MainNavKey.TvShowDetails> { key ->
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = "TV Show Details Screen: ${key.id}",
                color = Color.White
            )
        }
    }
}
