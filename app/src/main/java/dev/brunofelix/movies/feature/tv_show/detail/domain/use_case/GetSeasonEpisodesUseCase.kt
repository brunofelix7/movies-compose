package dev.brunofelix.movies.feature.tv_show.detail.domain.use_case

import dev.brunofelix.movies.core.domain.model.Episode
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetSeasonEpisodesUseCase {
    suspend operator fun invoke(tvShowId: Long, seasonNumber: Int): Resource<List<Episode>>
}

class GetSeasonEpisodesUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetSeasonEpisodesUseCase {
    override suspend fun invoke(tvShowId: Long, seasonNumber: Int): Resource<List<Episode>> {
        return repository.getSeasonEpisodes(tvShowId, seasonNumber)
    }
}
