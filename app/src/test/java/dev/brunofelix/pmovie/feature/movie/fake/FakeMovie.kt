package dev.brunofelix.pmovie.feature.movie.fake

sealed class FakeMovie {
    data object JohnWick : FakeMovie()
    data object Avengers : FakeMovie()
    data object AlienRomulus : FakeMovie()
}