package dev.brunofelix.pmovie.feature.movie.fake

import androidx.paging.PagingData
import dev.brunofelix.pmovie.feature.movie.factory.MovieFactory
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