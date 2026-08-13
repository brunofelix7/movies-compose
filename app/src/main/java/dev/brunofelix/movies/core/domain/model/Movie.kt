package dev.brunofelix.movies.core.domain.model

data class Movie(
    val id: Long = 0L,
    val title: String = "",
    val originalTitle: String = "",
    val originalLanguage: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val adult: Boolean = false,
    val genreIds: List<Int> = emptyList(),
    val popularity: Double = 0.0,
    val video: Boolean = false,
    val voteAverage: Float = 0f,
    val voteCount: Int = 0,
    val budget: Int = 0,
    val genres: List<MovieGenre> = emptyList(),
    val homepage: String = "",
    val imdbId: String = "",
    val originCountry: List<String> = emptyList(),
    val revenue: Long = 0L,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val duration: Int = 0
) {
    companion object {
        fun mock() = Movie(
            id = 1L,
            title = "John Wick: Chapter 4",
            posterPath = "/8vgNXm0YS6vIu9pYWq9Xv6clvYf.jpg",
            voteAverage = 7.8f,
            duration = 169,
            releaseDate = "2023-03-22",
            overview = "John Wick uncovers a path to defeating the High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes."
        )

        fun mocks() = listOf(
            mock(),
            mock().copy(id = 2L, title = "Avengers: Endgame"),
            mock().copy(id = 3L, title = "Alien: Romulus"),
            mock().copy(id = 4L, title = "The Godfather"),
            mock().copy(id = 5L, title = "Pulp Fiction")
        )
    }
}
