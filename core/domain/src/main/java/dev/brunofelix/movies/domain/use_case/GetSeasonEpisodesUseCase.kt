package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.util.Resource

fun interface GetSeasonEpisodesUseCase {
    suspend operator fun invoke(tvShowId: Long, seasonNumber: Int): Resource<List<Episode>>
}
