package dev.brunofelix.movies.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Colors.white,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.blackPrimary,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        ),
        actions = {
            IconButton(
                content = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        tint = Colors.white,
                        contentDescription = stringResource(R.string.top_bar_search_icon)
                    )
                },
                onClick = {}
            )
            IconButton(
                content = {
                    Icon(
                        imageVector = Icons.Filled.Language,
                        tint = Colors.white,
                        contentDescription = stringResource(R.string.top_bar_language_icon)
                    )
                },
                onClick = {}
            )
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MainTopBar(
        title = stringResource(R.string.upcoming)
    )
}