package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R

@Composable
fun EmptyImage(
    modifier: Modifier = Modifier
) {
    Icon(
        // Same glyph as EmptyState, so a card with no artwork reads like any other empty slot.
        imageVector = Icons.Outlined.Inbox,
        contentDescription = stringResource(R.string.empty_icon),
        tint = Color.White,
        modifier = modifier.size(40.dp)
    )
}

@Preview
@Composable
private fun EmptyImagePreview() {
    EmptyImage()
}