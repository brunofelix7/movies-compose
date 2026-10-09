package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.ReleaseType
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetMovieReleasesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieReleasesUseCase {

    override suspend operator fun invoke(month: ReleaseMonth, type: ReleaseType, page: Int): Resource<List<Movie>> {
        return repository.getReleases(month, type, page)
    }
}
