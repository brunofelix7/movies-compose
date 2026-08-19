package dev.brunofelix.movies.feature.search.domain.use_case

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

fun interface SearchUseCase {
    suspend operator fun invoke(query: String, page: Int): Resource<List<Media>>
}

class SearchUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : SearchUseCase {

    override suspend fun invoke(query: String, page: Int): Resource<List<Media>> {
        return repository.search(query, page)
    }
}
