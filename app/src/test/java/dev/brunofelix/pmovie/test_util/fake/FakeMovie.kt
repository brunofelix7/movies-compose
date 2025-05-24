package dev.brunofelix.pmovie.test_util.fake

sealed class FakeMovie {
    data object JohnWick : FakeMovie()
    data object Avengers : FakeMovie()
    data object AlienRomulus : FakeMovie()
}