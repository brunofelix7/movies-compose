package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.util.Resource

fun interface SearchMediaUseCase {
    suspend operator fun invoke(query: String, page: Int): Resource<List<Media>>
}
