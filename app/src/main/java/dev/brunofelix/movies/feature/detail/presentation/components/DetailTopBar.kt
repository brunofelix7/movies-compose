package dev.brunofelix.movies.feature.detail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "")
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.background(
                    color = Colors.blackPrimary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(24.dp)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    tint = Colors.white,
                    contentDescription = stringResource(R.string.top_bar_favorite_icon)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.background(
                    color = Colors.blackPrimary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(24.dp)
                )
            ) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    tint = if (isFavorite) Colors.redPrimary else Colors.white,
                    contentDescription = stringResource(R.string.top_bar_favorite_icon)
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )
}

@Preview
@Composable
private fun Preview() {
    DetailTopBar(
        isFavorite = true,
        onBackClick = {},
        onFavoriteClick = {}
    )
}