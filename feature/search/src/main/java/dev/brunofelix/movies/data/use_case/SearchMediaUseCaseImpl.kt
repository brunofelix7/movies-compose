package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.repository.SearchRepository
import dev.brunofelix.movies.domain.use_case.SearchMediaUseCase
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class SearchMediaUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : SearchMediaUseCase {

    override suspend operator fun invoke(query: String, page: Int): Resource<List<Media>> {
        return repository.search(query, page)
    }
}
