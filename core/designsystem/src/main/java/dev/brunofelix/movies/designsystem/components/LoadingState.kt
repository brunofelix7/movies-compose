package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.size36

const val LOADING_STATE_TEST_TAG = "loading_state"

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
        modifier = modifier
            .fillMaxSize()
            .testTag(LOADING_STATE_TEST_TAG),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(size36)
        )
    }
}

@Preview
@Composable
private fun LoadingStatePreview() {
    PMovieTheme {
        LoadingState()
    }
}
