package dev.brunofelix.movies.feature.tv_show.home.domain.use_case

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetPopularTvShowsUseCase {
    operator fun invoke(): Flow<PagingData<TvShow>>
}

class GetPopularTvShowsUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetPopularTvShowsUseCase {
    override fun invoke(): Flow<PagingData<TvShow>> {
        return repository.getPopularTvShows(PagingConfig(pageSize = 20))
    }
}
