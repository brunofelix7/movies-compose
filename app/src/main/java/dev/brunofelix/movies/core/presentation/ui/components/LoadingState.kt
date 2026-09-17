package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

/**
 * Spinner shown while content is loading.
 *
 * [verticalArrangement] lets a caller pin it to the top instead of centring it, which the
 * search overlay needs so the spinner stays right under the search bar.
 */
@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = Colors.redPrimary,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Preview
@Composable
private fun LoadingStatePreview() {
    LoadingState()
}