package dev.brunofelix.movies.feature.upcoming.presentation.state

import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieUpcomingUiState(
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)