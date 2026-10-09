package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.designsystem.R
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.size72
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8

private const val ICON_ALPHA = 0.6f

/**
 * Placeholder for a list that came back with nothing.
 *
 * [verticalArrangement] lets a caller pin it to the top instead of centring it, which the
 * search overlay needs so the keyboard does not sit on top of the message.
 */
@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.no_results_found),
    verticalArrangement: Arrangement.Vertical = Arrangement.Center
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing16),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            // Not a magnifier: the top bar uses SearchOff as the close-search action.
            imageVector = Icons.Outlined.Inbox,
            contentDescription = null,
            // Size only: padding here would inset the glyph inside the box and squash it.
            modifier = Modifier.size(size72),
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = ICON_ALPHA)
        )
        Spacer(modifier = Modifier.height(spacing8))
        Text(
            text = message,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun EmptyStatePreview() {
    PMovieTheme {
        EmptyState()
    }
}
