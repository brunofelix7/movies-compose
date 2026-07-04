package dev.brunofelix.movies.feature.detail.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.components.CustomButton
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.ui.resources.Colors
import dev.brunofelix.movies.feature.detail.presentation.state.DetailUiActions
import dev.brunofelix.movies.feature.detail.presentation.state.DetailUiState

@Composable
fun DetailContent(
    state: DetailUiState,
    actions: DetailUiActions,
    modifier: Modifier = Modifier
) {
    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (state.uiState) {
                is UiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LoadingState()
                    }
                }
                is UiState.Success -> {
                    LazyColumn {
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                        item {
                            Row {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column {
                                        Text(
                                            text = state.uiState.data.title,
                                            color = Colors.white,
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.CalendarMonth,
                                                tint = Colors.redPrimary,
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Text(
                                                text = state.uiState.data.releaseDate,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal,
                                                color = Colors.lightGray,
                                                modifier = Modifier.padding(start = 4.dp)
                                            )
                                            Spacer(Modifier.size(12.dp))
                                            Icon(
                                                imageVector = Icons.Outlined.Timer,
                                                tint = Colors.redPrimary,
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Text(
                                                text = "${if (state.uiState.data.duration <= 0) "--" else state.uiState.data.duration}min",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal,
                                                color = Colors.lightGray,
                                                modifier = Modifier.padding(start = 4.dp)
                                            )
                                        }
                                        Spacer(Modifier.size(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            state.uiState.data.genres.forEach { category ->
                                                Text(
                                                    text = category.name,
                                                    fontSize = 14.sp,
                                                    color = Colors.lightGray,
                                                    fontWeight = FontWeight.SemiBold,
                                                    modifier = Modifier.padding(bottom = 8.dp)
                                                )
                                                Spacer(Modifier.size(8.dp))
                                            }
                                        }
                                        Spacer(Modifier.size(8.dp))
                                    }
                                }
                            }
                            CustomButton(
                                text = "Watch Trailer",
                                icon = Icons.Filled.Movie,
                                isOutlined = false,
                                onClick = actions.onWatchTrailer
                            )
                            Text(
                                text = "Overview",
                                color = Colors.lightGray,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp, top = 16.dp)
                            )
                            Text(
                                text = state.uiState.data.overview,
                                color = Colors.white,
                                fontWeight = FontWeight.Light,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }
                else -> {
                    ErrorLayout(
                        modifier = Modifier.padding(top = 64.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    DetailContent(
        state = DetailUiState(),
        actions = DetailUiActions()
    )
}

@Preview
@Composable
private fun ErrorPreview() {
    DetailContent(
        state = DetailUiState(
            uiState = UiState.Error(0)
        ),
        actions = DetailUiActions()
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    DetailContent(
        state = DetailUiState(
            uiState = UiState.Success(
                data = MovieUiState(
                    title = "Super Mario Galaxy",
                    releaseDate = "01/04/2026",
                    duration = 120,
                    overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                )
            )
        ),
        actions = DetailUiActions()
    )
}
