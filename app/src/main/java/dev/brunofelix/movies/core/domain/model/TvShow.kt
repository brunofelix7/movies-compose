package dev.brunofelix.movies.core.domain.model

data class TvShow(
    val id: Long = 0L,
    val name: String = "",
    val originalName: String = "",
    val originalLanguage: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val firstAirDate: String = "",
    val genreIds: List<Int> = emptyList(),
    val popularity: Double = 0.0,
    val voteAverage: Float = 0f,
    val voteCount: Int = 0,
    val genres: List<MovieGenre> = emptyList(),
    val homepage: String = "",
    val originCountry: List<String> = emptyList(),
    val status: String = "",
    val tagline: String = "",
    val numberOfEpisodes: Int = 0,
    val numberOfSeasons: Int = 0,
    val type: String = ""
) {
    companion object {
        fun mock() = TvShow(
            id = 1L,
            name = "The Last of Us",
            posterPath = "/uKvH56B89aqo76q9U3syEb1SdiP.jpg",
            voteAverage = 8.6f,
            firstAirDate = "2023-01-15",
            overview = "Twenty years after modern civilization has been destroyed, Joel, a hardened survivor, is hired to smuggle Ellie, a 14-year-old girl, out of an oppressive quarantine zone. What starts as a small job soon becomes a brutal, heartbreaking journey, as they both must traverse the U.S. and depend on each other for survival."
        )

        fun mocks() = listOf(
            mock(),
            mock().copy(id = 2L, name = "Breaking Bad"),
            mock().copy(id = 3L, name = "The Boys"),
            mock().copy(id = 4L, name = "Stranger Things"),
            mock().copy(id = 5L, name = "House of the Dragon")
        )
    }
}
