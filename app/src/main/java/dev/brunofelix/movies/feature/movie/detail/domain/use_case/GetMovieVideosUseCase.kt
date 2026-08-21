package dev.brunofelix.movies.feature.movie.detail.domain.use_case

import dev.brunofelix.movies.core.domain.model.Video
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetMovieVideosUseCase {
    suspend operator fun invoke(id: Long): Resource<List<Video>>
}

class GetMovieVideosUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieVideosUseCase {
    override suspend fun invoke(id: Long): Resource<List<Video>> {
        return repository.getVideos(id)
    }
}
