package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.use_case.GetTvShowVideosUseCase
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetTvShowVideosUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetTvShowVideosUseCase {

    override suspend operator fun invoke(id: Long): Resource<List<Video>> {
        return repository.getVideos(id)
    }
}
