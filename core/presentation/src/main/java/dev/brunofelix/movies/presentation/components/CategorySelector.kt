package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.components.SelectorChip
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.model.Category

/**
 * Row of equally wide chips to pick one of the [categories].
 */
@Composable
fun CategorySelector(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing16),
        horizontalArrangement = Arrangement.spacedBy(spacing8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categories.forEach { category ->
            SelectorChip(
                label = stringResource(id = category.titleResId),
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                stretchLabel = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private enum class PreviewCategory(override val titleResId: Int) : Category {
    MOVIES(R.string.movies),
    TV_SHOWS(R.string.tv_shows)
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        CategorySelector(
            categories = PreviewCategory.entries,
            selectedCategory = PreviewCategory.MOVIES,
            onCategorySelected = {}
        )
    }
}
