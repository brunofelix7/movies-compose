package dev.brunofelix.movies.data.remote

import dev.brunofelix.movies.data.test_util.ApiTestFactory
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockWebServer

private const val MOVIE_PAGE_JSON = """
{
  "page": 1,
  "dates": { "maximum": "2024-02-01", "minimum": "2024-01-01" },
  "results": [
    {
      "adult": false,
      "backdrop_path": "/backdrop.jpg",
      "genre_ids": [28, 12],
      "id": 693134,
      "original_language": "en",
      "original_title": "Dune: Part Two",
      "overview": "Follow the mythic journey of Paul Atreides.",
      "popularity": 1234.56,
      "poster_path": "/poster.jpg",
      "release_date": "2024-02-27",
      "title": "Dune: Part Two",
      "video": false,
      "vote_average": 8.2,
      "vote_count": 5000,
      "unexpected_field": "ignored"
    },
    { "id": 2, "title": null, "poster_path": null }
  ],
  "total_pages": 10,
  "total_results": 200
}
"""

private const val MOVIE_DETAILS_JSON = """
{
  "id": 693134,
  "title": "Dune: Part Two",
  "budget": 190000000,
  "revenue": 714444358,
  "runtime": 167,
  "genres": [{ "id": 878, "name": "Science Fiction" }],
  "imdb_id": "tt15239678",
  "origin_country": ["US"],
  "status": "Released",
  "tagline": "Long live the fighters.",
  "belongs_to_collection": { "id": 1, "name": "Dune Collection" }
}
"""

private const val VIDEOS_JSON = """
{
  "id": 693134,
  "results": [
    { "id": "v1", "iso_639_1": "en", "iso_3166_1": "US", "key": "Way9Dexny3w", "name": "Official Trailer",
      "site": "YouTube", "size": 1080, "type": "Trailer", "official": true, "published_at": "2023-05-03T14:00:00.000Z" }
  ]
}
"""

private const val CREDITS_JSON = """
{
  "id": 693134,
  "cast": [
    { "id": 1190668, "name": "Timothée Chalamet", "character": "Paul Atreides", "profile_path": "/p.jpg", "order": 0,
      "known_for_department": "Acting" }
  ],
  "crew": []
}
"""

private const val WATCH_PROVIDERS_JSON = """
{
  "id": 693134,
  "results": {
    "BR": {
      "link": "https://www.themoviedb.org/movie/693134-dune-part-two/watch?locale=BR",
      "flatrate": [
        { "logo_path": "/max.jpg", "provider_id": 1899, "provider_name": "Max", "display_priority": 5 }
      ],
      "rent": [
        { "logo_path": "/apple.jpg", "provider_id": 2, "provider_name": "Apple TV", "display_priority": 4 }
      ]
    },
    "US": {
      "ads": [{ "logo_path": "/tubi.jpg", "provider_id": 73, "provider_name": "Tubi TV", "display_priority": 9 }]
    }
  }
}
"""

class MovieApiTest : DescribeSpec({

    val server = MockWebServer()
    lateinit var api: MovieApi

    beforeSpec {
        server.start()
        api = ApiTestFactory.create(server)
    }
    afterSpec { server.close() }

    describe("list endpoints") {
        it("should request the popular movies page and parse it") {
            runTest {
                server.enqueue(ApiTestFactory.json(MOVIE_PAGE_JSON))

                val body = api.getPopulars(page = 1).body()!!

                server.takeRequest().target shouldBe "/movie/popular?page=1"
                body.page shouldBe 1
                body.totalPages shouldBe 10
                body.dates?.maximum shouldBe "2024-02-01"
                val movie = body.results!!.first()
                movie.id shouldBe 693134L
                movie.title shouldBe "Dune: Part Two"
                movie.genreIds shouldBe listOf(28, 12)
                movie.voteAverage shouldBe 8.2f
                movie.popularity shouldBe 1234.56
                movie.posterPath shouldBe "/poster.jpg"
            }
        }

        it("should accept null and missing fields") {
            runTest {
                server.enqueue(ApiTestFactory.json(MOVIE_PAGE_JSON))

                val movie = api.getPopulars(page = 1).body()!!.results!![1]

                server.takeRequest()
                movie.title.shouldBeNull()
                movie.posterPath.shouldBeNull()
                movie.voteAverage.shouldBeNull()
            }
        }

        it("should request the upcoming and top rated pages") {
            runTest {
                server.enqueue(ApiTestFactory.json(MOVIE_PAGE_JSON))
                server.enqueue(ApiTestFactory.json(MOVIE_PAGE_JSON))

                api.getUpcoming(page = 2)
                api.getTopRated(page = 3)

                server.takeRequest().target shouldBe "/movie/upcoming?page=2"
                server.takeRequest().target shouldBe "/movie/top_rated?page=3"
            }
        }

        it("should send the search query and page") {
            runTest {
                server.enqueue(ApiTestFactory.json(MOVIE_PAGE_JSON))

                api.search(query = "dune", page = 1)

                val url = server.takeRequest().url
                url.encodedPath shouldBe "/search/movie"
                url.queryParameter("query") shouldBe "dune"
                url.queryParameter("page") shouldBe "1"
            }
        }

        it("should send the discover filters") {
            runTest {
                server.enqueue(ApiTestFactory.json(MOVIE_PAGE_JSON))

                api.discover(
                    startDate = "2024-01-01",
                    endDate = "2024-01-31",
                    releaseType = "2|3",
                    sortBy = "popularity.desc",
                    page = 4
                )

                val url = server.takeRequest().url
                url.encodedPath shouldBe "/discover/movie"
                url.queryParameter("release_date.gte") shouldBe "2024-01-01"
                url.queryParameter("release_date.lte") shouldBe "2024-01-31"
                url.queryParameter("with_release_type") shouldBe "2|3"
                url.queryParameter("sort_by") shouldBe "popularity.desc"
                url.queryParameter("page") shouldBe "4"
            }
        }
    }

    describe("getDetails") {
        it("should request the movie and parse the details") {
            runTest {
                server.enqueue(ApiTestFactory.json(MOVIE_DETAILS_JSON))

                val movie = api.getDetails(id = 693134L).body()!!

                server.takeRequest().target shouldBe "/movie/693134"
                movie.runtime shouldBe 167
                movie.budget shouldBe 190000000
                movie.revenue shouldBe 714444358L
                movie.genres!!.single().name shouldBe "Science Fiction"
                movie.originCountry shouldBe listOf("US")
                movie.imdbId shouldBe "tt15239678"
            }
        }
    }

    describe("getVideos") {
        it("should request the videos and parse them") {
            runTest {
                server.enqueue(ApiTestFactory.json(VIDEOS_JSON))

                val video = api.getVideos(id = 693134L).body()!!.results!!.single()

                server.takeRequest().target shouldBe "/movie/693134/videos"
                video.key shouldBe "Way9Dexny3w"
                video.site shouldBe "YouTube"
                video.official shouldBe true
            }
        }
    }

    describe("getCredits") {
        it("should request the credits and parse the cast") {
            runTest {
                server.enqueue(ApiTestFactory.json(CREDITS_JSON))

                val credits = api.getCredits(id = 693134L).body()!!

                server.takeRequest().target shouldBe "/movie/693134/credits"
                credits.cast!!.single().character shouldBe "Paul Atreides"
                credits.cast.single().order shouldBe 0
            }
        }

        it("should parse an empty cast") {
            runTest {
                server.enqueue(ApiTestFactory.json("""{ "id": 1, "cast": [] }"""))

                val credits = api.getCredits(id = 1L).body()!!

                server.takeRequest()
                credits.cast!!.shouldBeEmpty()
            }
        }
    }

    describe("getWatchProviders") {
        it("should request the watch providers and parse every region") {
            runTest {
                server.enqueue(ApiTestFactory.json(WATCH_PROVIDERS_JSON))

                val results = api.getWatchProviders(id = 693134L).body()!!.results!!

                server.takeRequest().target shouldBe "/movie/693134/watch/providers"
                results.keys shouldBe setOf("BR", "US")
                val provider = results.getValue("BR").flatrate!!.single()
                provider.providerId shouldBe 1899L
                provider.providerName shouldBe "Max"
                provider.logoPath shouldBe "/max.jpg"
                provider.displayPriority shouldBe 5
                results.getValue("US").ads!!.single().providerName shouldBe "Tubi TV"
            }
        }

        it("should parse a title without any offer") {
            runTest {
                server.enqueue(ApiTestFactory.json("""{ "id": 1, "results": {} }"""))

                val body = api.getWatchProviders(id = 1L).body()!!

                server.takeRequest()
                body.results!!.keys.shouldBeEmpty()
            }
        }
    }
})
