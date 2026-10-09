package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeCircle
import dev.brunofelix.movies.designsystem.theme.size40
import dev.brunofelix.movies.designsystem.theme.spacing24
import dev.brunofelix.movies.designsystem.theme.spacing8

private const val UNSELECTED_BORDER_ALPHA = 0.5f

/** Gap between the top bar and a selector row. */
val SelectorTopSpacing = spacing8

/**
 * Gap between a selector row and the content it filters. The releases screen also uses it as
 * the spacing between its sections, which is what keeps that first gap identical to this one.
 */
val SelectorContentSpacing = spacing24

/**
 * Capsule shaped filter chip shared by the favorites category selector and the release month
 * selector, so both filter rows stay identical. It is taller than the 32dp Material default,
 * which reads too cramped for a filter row.
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
        shape = shapeCircle,
        modifier = modifier.height(size40),
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
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            selectedLabelColor = MaterialTheme.colorScheme.onSecondary
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = UNSELECTED_BORDER_ALPHA),
            selectedBorderColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(spacing8)) {
            SelectorChip(label = "Movies", isSelected = true, onClick = {})
            SelectorChip(label = "TV Shows", isSelected = false, onClick = {})
        }
    }
}
