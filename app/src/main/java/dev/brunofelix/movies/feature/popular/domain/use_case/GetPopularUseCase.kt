package dev.brunofelix.movies.feature.popular.domain.use_case

import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

fun interface GetPopularUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}