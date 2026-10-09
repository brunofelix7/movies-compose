package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.designsystem.R
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.size40

@Composable
fun EmptyImage(
    modifier: Modifier = Modifier
) {
    Icon(
        // Same glyph as EmptyState, so a card with no artwork reads like any other empty slot.
        imageVector = Icons.Outlined.Inbox,
        contentDescription = stringResource(R.string.empty_icon),
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.size(size40)
    )
}

@Preview
@Composable
private fun EmptyImagePreview() {
    PMovieTheme {
        EmptyImage()
    }
}
