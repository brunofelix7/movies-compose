package dev.brunofelix.movies.presentation.mapper

import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.model.Season
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.presentation.model.EpisodeUiModel
import dev.brunofelix.movies.presentation.model.SeasonUiModel
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.Locale

class TvShowMappersTest : DescribeSpec({

    val defaultLocale = Locale.getDefault()

    beforeSpec { Locale.setDefault(Locale.US) }
    afterSpec { Locale.setDefault(defaultLocale) }

    describe("Season.toUiModel") {
        it("should keep only the air year") {
            Season(id = 1L, name = "Season 1", seasonNumber = 1, episodeCount = 9, airDate = "2023-01-15")
                .toUiModel() shouldBe SeasonUiModel(
                    id = 1L,
                    name = "Season 1",
                    seasonNumber = 1,
                    episodeCount = 9,
                    airYear = "2023"
                )
        }
    }

    describe("Episode.toUiModel") {
        it("should format the runtime and the air date") {
            Episode(
                id = 2L,
                name = "Infected",
                overview = "Overview",
                stillPath = "still",
                episodeNumber = 2,
                runtime = 53,
                airDate = "2023-01-22"
            ).toUiModel() shouldBe EpisodeUiModel(
                id = 2L,
                name = "Infected",
                overview = "Overview",
                stillPath = "still",
                episodeNumber = 2,
                runtime = "53min",
                airDate = "22/01/2023"
            )
        }

        it("should show a placeholder for a missing runtime") {
            Episode(runtime = 0).toUiModel().runtime shouldBe "--min"
        }
    }

    describe("TvShow.toUiModel") {
        it("should format the fields and drop specials and empty seasons") {
            val uiModel = TvShow(
                id = 5L,
                name = "Dark",
                overview = "Overview",
                posterPath = "poster",
                backdropPath = "backdrop",
                firstAirDate = "2017-12-01",
                voteAverage = 8.4f,
                numberOfEpisodes = 26,
                numberOfSeasons = 3,
                seasons = listOf(
                    Season(id = 1L, seasonNumber = 0, episodeCount = 2),
                    Season(id = 2L, seasonNumber = 1, episodeCount = 10),
                    Season(id = 3L, seasonNumber = 2, episodeCount = 0)
                )
            ).toUiModel()

            uiModel.id shouldBe 5L
            uiModel.name shouldBe "Dark"
            uiModel.firstAirDate shouldBe "01/12/2017"
            uiModel.voteAverage shouldBe "8.4"
            uiModel.numberOfEpisodes shouldBe 26
            uiModel.numberOfSeasons shouldBe 3
            uiModel.seasons.map { it.id } shouldBe listOf(2L)
        }

        it("should show a placeholder for a missing rating") {
            TvShow(voteAverage = 0f).toUiModel().voteAverage shouldBe "--"
        }
    }
})
