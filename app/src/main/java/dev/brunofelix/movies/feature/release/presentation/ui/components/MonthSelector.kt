package dev.brunofelix.movies.feature.release.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme

/**
 * Horizontal month picker for the release calendar. It scrolls to the selected month so the
 * current one is visible when the screen opens.
 */
@Composable
fun MonthSelector(
    months: List<ReleaseMonth>,
    selectedMonth: ReleaseMonth,
    modifier: Modifier = Modifier,
    onMonthSelected: (ReleaseMonth) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val selectedIndex = months.indexOf(selectedMonth)

    LaunchedEffect(selectedIndex) {
        if (selectedIndex >= 0) listState.animateScrollToItem(selectedIndex)
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(
            items = months,
            key = { _, month -> month.id }
        ) { _, month ->
            val isSelected = month == selectedMonth

            FilterChip(
                selected = isSelected,
                onClick = { onMonthSelected(month) },
                label = {
                    Text(
                        text = month.label(),
                        style = MaterialTheme.typography.bodyMedium
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
    }
}

@Preview
@Composable
private fun Preview() {
    val months = ReleaseMonth.window(monthsBack = 2, monthsForward = 2)

    PMovieTheme {
        MonthSelector(
            months = months,
            selectedMonth = ReleaseMonth.current()
        )
    }
}
