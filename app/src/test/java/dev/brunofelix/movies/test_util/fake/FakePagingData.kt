package dev.brunofelix.movies.test_util.fake

import androidx.paging.PagingData
import dev.brunofelix.movies.test_util.factory.MovieFactory
import dev.brunofelix.movies.feature.movie.domain.model.Movie

object FakePagingData {

    val fakeMovies: PagingData<Movie> = PagingData.from(
        listOf(
            MovieFactory().create(FakeMovie.JohnWick),
            MovieFactory().create(FakeMovie.Avengers),
            MovieFactory().create(FakeMovie.AlienRomulus)
        )
    )
}