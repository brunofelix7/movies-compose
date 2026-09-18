package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

/** Taller than the 32.dp Material default, which reads too cramped for a filter row. */
private val ChipHeight = 40.dp

/**
 * Capsule shaped filter chip shared by the favorites category selector and the release month
 * selector, so both filter rows stay identical.
 *
 * @param stretchLabel centres the label across the whole chip. Needed when the caller sizes the
 * chip with a weight; a chip that wraps its content must not stretch, or it would try to grow
 * into the unbounded width of a scrolling row.
 */
@Composable
fun SelectorChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    stretchLabel: Boolean = false
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier.height(ChipHeight),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = if (stretchLabel) Modifier.fillMaxWidth() else Modifier
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.Transparent,
            labelColor = Colors.lightGray,
            selectedContainerColor = Colors.white,
            selectedLabelColor = Colors.blackPrimary
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = Colors.lightGray.copy(alpha = 0.5F),
            selectedBorderColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun Preview() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SelectorChip(label = "Movies", isSelected = true, onClick = {})
        SelectorChip(label = "TV Shows", isSelected = false, onClick = {})
    }
}
