package dev.brunofelix.movies.feature.tv_show.detail.domain.use_case

import dev.brunofelix.movies.core.domain.model.Video
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetTvShowVideosUseCase {
    suspend operator fun invoke(id: Long): Resource<List<Video>>
}

class GetTvShowVideosUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetTvShowVideosUseCase {
    override suspend fun invoke(id: Long): Resource<List<Video>> {
        return repository.getVideos(id)
    }
}
