package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.util.Resource

/**
 * Loads one page of any of the lists reachable from a "View more" action.
 */
fun interface GetMediaListUseCase {
    suspend operator fun invoke(
        category: MediaListCategory,
        month: ReleaseMonth?,
        page: Int
    ): Resource<List<Media>>
}
