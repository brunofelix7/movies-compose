package dev.brunofelix.movies.data.local.source

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import android.telephony.TelephonyManager
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.util.Locale

class RegionLocalDataSourceImplTest : DescribeSpec({

    fun dataSource(
        simCountry: String? = "",
        networkCountry: String? = "",
        systemLocale: Locale = Locale.ENGLISH,
        hasTelephony: Boolean = true
    ): RegionLocalDataSourceImpl {
        val localeList = mockk<LocaleList> { every { get(0) } returns systemLocale }
        val configuration = mockk<Configuration> { every { locales } returns localeList }
        val systemResources = mockk<Resources> { every { this@mockk.configuration } returns configuration }
        every { Resources.getSystem() } returns systemResources

        val telephony = mockk<TelephonyManager> {
            every { simCountryIso } returns simCountry
            every { networkCountryIso } returns networkCountry
        }
        val context = mockk<Context> {
            every { getSystemService(Context.TELEPHONY_SERVICE) } returns telephony.takeIf { hasTelephony }
        }
        return RegionLocalDataSourceImpl(context)
    }

    beforeTest { mockkStatic(Resources::class) }
    afterTest { unmockkStatic(Resources::class) }

    describe("getRegion") {
        it("should use the SIM country in upper case") {
            dataSource(simCountry = "br", networkCountry = "us").getRegion() shouldBe "BR"
        }

        it("should fall back to the network country when the SIM has none") {
            dataSource(simCountry = "", networkCountry = "pt").getRegion() shouldBe "PT"
        }

        it("should fall back to the system locale without telephony") {
            dataSource(systemLocale = Locale.forLanguageTag("pt-BR"), hasTelephony = false).getRegion() shouldBe "BR"
        }

        it("should default to US when no source knows the region") {
            dataSource(simCountry = null, networkCountry = null, systemLocale = Locale.ENGLISH).getRegion() shouldBe "US"
        }
    }
})
