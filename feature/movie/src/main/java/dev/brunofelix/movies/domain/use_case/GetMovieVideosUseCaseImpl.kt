package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetMovieVideosUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieVideosUseCase {

    override suspend operator fun invoke(id: Long): Resource<List<Video>> {
        return repository.getVideos(id)
    }
}
