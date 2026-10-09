package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.components.SelectorChip
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.domain.model.ReleaseMonth

private const val PREVIEW_MONTHS_AROUND = 2

/**
 * Horizontal month picker for the release calendar. It scrolls to the selected month so the
 * current one is visible when the screen opens.
 */
@Composable
internal fun MonthSelector(
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
        contentPadding = PaddingValues(horizontal = spacing16),
        horizontalArrangement = Arrangement.spacedBy(spacing8),
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(
            items = months,
            key = { _, month -> month.id }
        ) { _, month ->
            SelectorChip(
                label = month.label(),
                isSelected = month == selectedMonth,
                onClick = { onMonthSelected(month) }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        MonthSelector(
            months = ReleaseMonth.window(monthsBack = PREVIEW_MONTHS_AROUND, monthsForward = PREVIEW_MONTHS_AROUND),
            selectedMonth = ReleaseMonth.current()
        )
    }
}
