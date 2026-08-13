package dev.brunofelix.movies.core.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun fetchPopulars(pagingConfig: PagingConfig): Flow<PagingData<TvShow>>
    fun fetchTopRated(pagingConfig: PagingConfig): Flow<PagingData<TvShow>>
    suspend fun getDetails(id: Long): Resource<TvShow>
}

