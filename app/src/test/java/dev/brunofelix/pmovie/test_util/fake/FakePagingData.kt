package dev.brunofelix.pmovie.test_util.fake

import androidx.paging.PagingData
import dev.brunofelix.pmovie.test_util.factory.MovieFactory
import dev.brunofelix.pmovie.feature.movie.domain.model.Movie

object FakePagingData {

    val fakeMovies: PagingData<Movie> = PagingData.from(
        listOf(
            MovieFactory().create(FakeMovie.JohnWick),
            MovieFactory().create(FakeMovie.Avengers),
            MovieFactory().create(FakeMovie.AlienRomulus)
        )
    )
}