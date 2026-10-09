package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.util.Resource

fun interface GetMovieVideosUseCase {
    suspend operator fun invoke(id: Long): Resource<List<Video>>
}
