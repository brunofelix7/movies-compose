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
)
