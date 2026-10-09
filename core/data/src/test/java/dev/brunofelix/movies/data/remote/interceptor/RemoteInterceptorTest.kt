package dev.brunofelix.movies.data.remote.interceptor

import dev.brunofelix.movies.core.data.BuildConfig
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.repository.LanguageRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import okhttp3.Request

class RemoteInterceptorTest : DescribeSpec({

    val server = MockWebServer()

    beforeSpec { server.start() }
    afterSpec { server.close() }

    describe("intercept") {
        it("should add the API key and the preferred language to the request") {
            val languageRepository = mockk<LanguageRepository> {
                every { getLanguage() } returns flowOf(LanguageEnum.PORTUGUESE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(RemoteInterceptor(languageRepository))
                .build()
            server.enqueue(MockResponse.Builder().code(200).body("{}").build())

            client.newCall(Request.Builder().url(server.url("/movie/popular?page=2")).build()).execute().close()

            val url = server.takeRequest().url
            url.queryParameter("page") shouldBe "2"
            url.queryParameter("api_key") shouldBe BuildConfig.API_KEY
            url.queryParameter("language") shouldBe "pt-BR"
        }
    }
})
