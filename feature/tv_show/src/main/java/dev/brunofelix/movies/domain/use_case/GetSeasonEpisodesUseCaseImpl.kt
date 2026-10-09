package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetSeasonEpisodesUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetSeasonEpisodesUseCase {

    override suspend operator fun invoke(tvShowId: Long, seasonNumber: Int): Resource<List<Episode>> {
        return repository.getSeasonEpisodes(tvShowId, seasonNumber)
    }
}
