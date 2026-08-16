package dev.brunofelix.movies.feature.search.domain.use_case

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface SearchUseCase {
    operator fun invoke(query: String): Flow<PagingData<Media>>
}

class SearchUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : SearchUseCase {

    override fun invoke(query: String): Flow<PagingData<Media>> {
        return repository.search(query, PagingConfig(pageSize = 40))
    }
}
