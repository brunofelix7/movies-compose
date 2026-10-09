package dev.brunofelix.movies.presentation.mapper

import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.MovieGenre
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.presentation.model.CastUiModel
import dev.brunofelix.movies.presentation.model.MediaUiModel
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.Locale

class MediaMappersTest : DescribeSpec({

    val defaultLocale = Locale.getDefault()

    beforeSpec { Locale.setDefault(Locale.US) }
    afterSpec { Locale.setDefault(defaultLocale) }

    describe("Cast.toUiModel") {
        it("should copy the cast fields") {
            Cast(id = 1L, name = "Pedro Pascal", character = "Joel", profilePath = "url").toUiModel() shouldBe
                CastUiModel(id = 1L, name = "Pedro Pascal", character = "Joel", profilePath = "url")
        }
    }

    describe("Media.toUiModel") {
        it("should format the date, the rating and the duration") {
            Media(
                id = 3L,
                title = "Dune",
                posterPath = "url",
                voteAverage = 7.86f,
                releaseDate = "2021-10-22",
                duration = 155,
                type = MediaType.MOVIE
            ).toUiModel() shouldBe MediaUiModel(
                id = 3L,
                title = "Dune",
                posterPath = "url",
                releaseDate = "22/10/2021",
                voteAverage = "7.9",
                duration = "155min",
                type = MediaType.MOVIE
            )
        }

        it("should show placeholders for a missing rating and duration") {
            val uiModel = Media(voteAverage = 0f, duration = 0).toUiModel()

            uiModel.voteAverage shouldBe "--"
            uiModel.duration shouldBe "--min"
            uiModel.releaseDate shouldBe "--"
        }
    }

    describe("Movie.toUiModel") {
        it("should format the duration in hours and minutes") {
            Movie(duration = 167).toUiModel().duration shouldBe "2h 47m"
            Movie(duration = 120).toUiModel().duration shouldBe "2h"
            Movie(duration = 45).toUiModel().duration shouldBe "45m"
            Movie(duration = 0).toUiModel().duration shouldBe "--"
        }

        it("should map the remaining movie fields") {
            val genres = listOf(MovieGenre(id = 1, name = "Action"))
            val uiModel = Movie(
                id = 9L,
                title = "Dune",
                overview = "Overview",
                posterPath = "poster",
                backdropPath = "backdrop",
                releaseDate = "2024-02-27",
                voteAverage = 8.2f,
                genres = genres
            ).toUiModel()

            uiModel.id shouldBe 9L
            uiModel.title shouldBe "Dune"
            uiModel.overview shouldBe "Overview"
            uiModel.posterPath shouldBe "poster"
            uiModel.backdropPath shouldBe "backdrop"
            uiModel.releaseDate shouldBe "27/02/2024"
            uiModel.voteAverage shouldBe "8.2"
            uiModel.genres shouldBe genres
        }

        it("should show a placeholder for a missing rating") {
            Movie(voteAverage = -1f).toUiModel().voteAverage shouldBe "--"
        }
    }
})
