package dev.brunofelix.movies.feature.movie.presentation.state

import androidx.paging.PagingData
import dev.brunofelix.movies.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieUpcomingUiState(
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)