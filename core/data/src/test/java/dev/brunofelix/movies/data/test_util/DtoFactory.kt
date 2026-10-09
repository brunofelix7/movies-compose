package dev.brunofelix.movies.data.test_util

import dev.brunofelix.movies.data.remote.dto.CastDto
import dev.brunofelix.movies.data.remote.dto.VideoDto
import dev.brunofelix.movies.data.remote.dto.WatchProviderDto
import dev.brunofelix.movies.data.remote.dto.movie.MovieDto
import dev.brunofelix.movies.data.remote.dto.movie.MovieGenreDto
import dev.brunofelix.movies.data.remote.dto.tv_show.EpisodeDto
import dev.brunofelix.movies.data.remote.dto.tv_show.SeasonDto
import dev.brunofelix.movies.data.remote.dto.tv_show.TvShowDto

object DtoFactory {

    fun movieDto(id: Long = 1L) = MovieDto(
        id = id,
        title = "Movie $id",
        overview = "Overview $id",
        posterPath = "/poster$id.jpg",
        backdropPath = "/backdrop$id.jpg",
        releaseDate = "2024-01-0$id",
        voteAverage = 7.5f,
        voteCount = 100,
        runtime = 120,
        genres = listOf(MovieGenreDto(id = 28, name = "Action"))
    )

    fun tvShowDto(id: Long = 1L) = TvShowDto(
        id = id,
        name = "Show $id",
        originalName = "Original $id",
        originalLanguage = "en",
        overview = "Overview $id",
        posterPath = "/poster$id.jpg",
        backdropPath = "/backdrop$id.jpg",
        firstAirDate = "2023-05-0$id",
        genreIds = listOf(18),
        popularity = 12.5,
        voteAverage = 8.2f,
        voteCount = 50,
        genres = listOf(MovieGenreDto(id = 18, name = "Drama")),
        homepage = "https://show.example",
        originCountry = listOf("US"),
        status = "Ended",
        tagline = "Tagline",
        numberOfEpisodes = 20,
        numberOfSeasons = 2,
        seasons = listOf(seasonDto()),
        type = "Scripted"
    )

    fun seasonDto(number: Int = 1) = SeasonDto(
        id = 10L + number,
        name = "Season $number",
        overview = "Season overview",
        posterPath = "/season$number.jpg",
        seasonNumber = number,
        episodeCount = 10,
        airDate = "2023-05-01",
        voteAverage = 7.9f,
        episodes = listOf(episodeDto())
    )

    fun episodeDto(number: Int = 1) = EpisodeDto(
        id = 100L + number,
        name = "Episode $number",
        overview = "Episode overview",
        stillPath = "/still$number.jpg",
        episodeNumber = number,
        seasonNumber = 1,
        runtime = 45,
        airDate = "2023-05-01",
        voteAverage = 8.0f
    )

    fun castDto(id: Long = 1L, order: Int? = 0) = CastDto(
        id = id,
        name = "Actor $id",
        character = "Character $id",
        profilePath = "/profile$id.jpg",
        order = order
    )

    fun videoDto(key: String = "abc") = VideoDto(
        id = "video-$key",
        key = key,
        name = "Trailer",
        site = "YouTube",
        type = "Trailer",
        official = true
    )

    fun watchProviderDto(id: Long = 8L, priority: Int? = 1) = WatchProviderDto(
        providerId = id,
        providerName = "Provider $id",
        logoPath = "/logo$id.jpg",
        displayPriority = priority
    )
}
