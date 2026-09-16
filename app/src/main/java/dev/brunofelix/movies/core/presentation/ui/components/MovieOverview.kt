package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

private const val CollapsedLines = 3

@Composable
fun MovieOverview(
    overview: String,
    modifier: Modifier = Modifier
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var isTruncated by remember { mutableStateOf(false) }

    SectionCard(
        title = stringResource(R.string.overview),
        modifier = modifier
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            Text(
                text = overview,
                color = Colors.white,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = if (isExpanded) Int.MAX_VALUE else CollapsedLines,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { result ->
                    if (!isExpanded) isTruncated = result.hasVisualOverflow
                }
            )
            if (isTruncated) {
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = if (isExpanded) {
                            Icons.Filled.KeyboardArrowUp
                        } else {
                            Icons.Filled.KeyboardArrowDown
                        },
                        tint = Colors.white,
                        contentDescription = stringResource(
                            if (isExpanded) R.string.overview_collapse else R.string.overview_expand
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MovieOverview(
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo.",
    )
}

@Preview
@Composable
private fun ShortPreview() {
    MovieOverview(overview = "Lorem ipsum dolor sit amet.")
}
