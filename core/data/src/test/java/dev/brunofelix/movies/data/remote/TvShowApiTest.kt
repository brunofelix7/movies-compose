package dev.brunofelix.movies.data.remote

import dev.brunofelix.movies.data.test_util.ApiTestFactory
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockWebServer

private const val TV_SHOW_PAGE_JSON = """
{
  "page": 1,
  "results": [
    {
      "backdrop_path": "/backdrop.jpg",
      "first_air_date": "2017-12-01",
      "genre_ids": [18, 9648],
      "id": 70523,
      "name": "Dark",
      "origin_country": ["DE"],
      "original_language": "de",
      "original_name": "Dark",
      "overview": "A missing child sets four families on a frantic hunt for answers.",
      "popularity": 99.5,
      "poster_path": "/poster.jpg",
      "vote_average": 8.4,
      "vote_count": 6000,
      "adult": false
    }
  ],
  "total_pages": 5,
  "total_results": 100
}
"""

private const val TV_SHOW_DETAILS_JSON = """
{
  "id": 70523,
  "name": "Dark",
  "number_of_episodes": 26,
  "number_of_seasons": 3,
  "status": "Ended",
  "type": "Scripted",
  "genres": [{ "id": 18, "name": "Drama" }],
  "seasons": [
    { "id": 1, "name": "Specials", "season_number": 0, "episode_count": 2, "air_date": null, "poster_path": null, "vote_average": 0 },
    { "id": 2, "name": "Season 1", "season_number": 1, "episode_count": 10, "air_date": "2017-12-01", "poster_path": "/s1.jpg", "vote_average": 8.1 }
  ],
  "created_by": [{ "id": 1, "name": "Baran bo Odar" }]
}
"""

private const val SEASON_JSON = """
{
  "id": 2,
  "name": "Season 1",
  "season_number": 1,
  "episodes": [
    { "id": 11, "name": "Secrets", "overview": "In 2019...", "still_path": "/e1.jpg", "episode_number": 1,
      "season_number": 1, "runtime": 51, "air_date": "2017-12-01", "vote_average": 8.0, "crew": [] }
  ]
}
"""

class TvShowApiTest : DescribeSpec({

    val server = MockWebServer()
    lateinit var api: TvShowApi

    beforeSpec {
        server.start()
        api = ApiTestFactory.create(server)
    }
    afterSpec { server.close() }

    describe("list endpoints") {
        it("should request the popular TV shows page and parse it") {
            runTest {
                server.enqueue(ApiTestFactory.json(TV_SHOW_PAGE_JSON))

                val body = api.getPopulars(page = 1).body()!!

                server.takeRequest().target shouldBe "/tv/popular?page=1"
                body.totalResults shouldBe 100
                val tvShow = body.results!!.single()
                tvShow.id shouldBe 70523L
                tvShow.name shouldBe "Dark"
                tvShow.firstAirDate shouldBe "2017-12-01"
                tvShow.originCountry shouldBe listOf("DE")
                tvShow.voteAverage shouldBe 8.4f
            }
        }

        it("should request the top rated page") {
            runTest {
                server.enqueue(ApiTestFactory.json(TV_SHOW_PAGE_JSON))

                api.getTopRated(page = 2)

                server.takeRequest().target shouldBe "/tv/top_rated?page=2"
            }
        }

        it("should send the search query and page") {
            runTest {
                server.enqueue(ApiTestFactory.json(TV_SHOW_PAGE_JSON))

                api.search(query = "dark", page = 3)

                val url = server.takeRequest().url
                url.encodedPath shouldBe "/search/tv"
                url.queryParameter("query") shouldBe "dark"
                url.queryParameter("page") shouldBe "3"
            }
        }

        it("should send the discover filters") {
            runTest {
                server.enqueue(ApiTestFactory.json(TV_SHOW_PAGE_JSON))

                api.discover(startDate = "2024-01-01", endDate = "2024-01-31", sortBy = "popularity.desc", page = 1)

                val url = server.takeRequest().url
                url.encodedPath shouldBe "/discover/tv"
                url.queryParameter("first_air_date.gte") shouldBe "2024-01-01"
                url.queryParameter("first_air_date.lte") shouldBe "2024-01-31"
                url.queryParameter("sort_by") shouldBe "popularity.desc"
            }
        }
    }

    describe("getDetails") {
        it("should request the TV show and parse its seasons") {
            runTest {
                server.enqueue(ApiTestFactory.json(TV_SHOW_DETAILS_JSON))

                val tvShow = api.getDetails(id = 70523L).body()!!

                server.takeRequest().target shouldBe "/tv/70523"
                tvShow.numberOfSeasons shouldBe 3
                tvShow.numberOfEpisodes shouldBe 26
                tvShow.seasons!!.map { it.seasonNumber } shouldBe listOf(0, 1)
                tvShow.seasons[0].airDate shouldBe null
                tvShow.seasons[1].posterPath shouldBe "/s1.jpg"
            }
        }
    }

    describe("getSeason") {
        it("should request the season and parse its episodes") {
            runTest {
                server.enqueue(ApiTestFactory.json(SEASON_JSON))

                val episode = api.getSeason(id = 70523L, seasonNumber = 1).body()!!.episodes!!.single()

                server.takeRequest().target shouldBe "/tv/70523/season/1"
                episode.name shouldBe "Secrets"
                episode.runtime shouldBe 51
                episode.stillPath shouldBe "/e1.jpg"
            }
        }
    }

    describe("getVideos and getCredits") {
        it("should request the videos and the credits of the TV show") {
            runTest {
                server.enqueue(ApiTestFactory.json("""{ "id": 1, "results": [{ "key": "k", "site": "YouTube" }] }"""))
                server.enqueue(ApiTestFactory.json("""{ "id": 1, "cast": [{ "id": 5, "name": "Louis Hofmann" }] }"""))

                val video = api.getVideos(id = 1L).body()!!.results!!.single()
                val cast = api.getCredits(id = 1L).body()!!.cast!!.single()

                server.takeRequest().target shouldBe "/tv/1/videos"
                server.takeRequest().target shouldBe "/tv/1/credits"
                video.key shouldBe "k"
                cast.name shouldBe "Louis Hofmann"
            }
        }
    }
})
